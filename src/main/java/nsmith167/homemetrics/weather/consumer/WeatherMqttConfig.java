package nsmith167.homemetrics.weather.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import nsmith167.homemetrics.weather.consumer.model.WeatherScannerMessage;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;

import java.io.IOException;

@Configuration
public class WeatherMqttConfig {

    @Autowired
    WeatherMqttConsumer weatherMqttConsumer;

    @Value("${mqtt.broker.url}")
    String url;

    @Value("${mqtt.clientId}")
    String clientId;

    @Value("${mqtt.topics.weather.name}")
    String topicName;

    @Value("${mqtt.username}")
    String username;

    @Value("${mqtt.password}")
    String password;

    @Bean
    public IntegrationFlow mqttInbound() {
        return IntegrationFlow.from(
                        new MqttPahoMessageDrivenChannelAdapter(url,
                                clientId, defaultMqttPahoClientFactory(), topicName))
                .handle(weatherMqttConsumer::consumeWeatherScannerMessage)
                .get();
    }

    @Bean
    public DefaultMqttPahoClientFactory defaultMqttPahoClientFactory() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(options);

        return factory;
    }
}
