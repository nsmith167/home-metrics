package nsmith167.homemetrics.config;

import nsmith167.homemetrics.temphumidity.consumer.TempHumidityMessageHandler;
import nsmith167.homemetrics.weather.consumer.WeatherScannerMessageHandler;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class MqttConfig {

    @Value("${mqtt.broker.url}")
    String url;

    @Value("${mqtt.clientId}")
    String clientId;

    @Value("${mqtt.topics.weather.name}")
    String weatherTopicName;

    @Value("${mqtt.topics.temphumidity.name}")
    String tempHumidityTopicName;

    @Value("${mqtt.username}")
    String username;

    @Value("${mqtt.password}")
    String password;

    @Autowired
    WeatherScannerMessageHandler weatherScannerMessageHandler;

    @Autowired
    TempHumidityMessageHandler tempHumidityMessageHandler;

    @Bean
    public MqttPahoMessageDrivenChannelAdapter mqttInbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(url, clientId, defaultMqttPahoClientFactory(), weatherTopicName, tempHumidityTopicName);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler messageHandler() {
        return message -> {
            String topic = (String) message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC);
            String payload = (String) message.getPayload();

            if (weatherTopicName.equals(topic)) {
                weatherScannerMessageHandler.handleWeatherScannerMessage(payload);
            } else if (tempHumidityTopicName.equals(topic)) {
                tempHumidityMessageHandler.handleTempHumidityMessage(payload);
            } else {
                throw new RuntimeException("Message received from unknown topic");
            }
        };
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
