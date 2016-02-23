/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.andes.transports.mqtt.commands.v300;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.andes.transports.mqtt.MqttChannel;
import org.wso2.carbon.andes.transports.mqtt.MqttConstants;
import org.wso2.carbon.andes.transports.mqtt.connectors.IConnector;
import org.wso2.carbon.andes.transports.mqtt.distribution.bridge.QOSLevel;
import org.wso2.carbon.andes.transports.mqtt.exceptions.ConnectorException;
import org.wso2.carbon.andes.transports.server.BrokerException;

import java.util.Map;

/**
 * Will be triggered to disconnect MQTT client
 */
public class Disconnect {

    private static final Log log = LogFactory.getLog(Disconnect.class);

    /**
     * Notifies the message store on receiving a published message
     *
     * @param messageStore specifies the message store which will be informed about the publish message
     * @param channel      specifies the connection information of the client
     * @return Subscribe
     */
    public static boolean notifyStore(IConnector messageStore, MqttChannel channel)
            throws
            BrokerException {

      //  String mqttClientChannelID = channel.getProperty(MqttConstants.CLIENT_ID_PROPERTY_NAME);

        try {
            //We need to also consider publisher disconnections here and clear the state
           // publisherAckReceiver.disconnectionPublisher(mqttClientChannelID);
            boolean isCleanSession = Boolean.parseBoolean(channel.getProperty(MqttConstants
                    .SESSION_DURABILITY_PROPERTY_NAME));

            for (Map.Entry<String, Integer> topicDetails : channel.getTopicList().entrySet()) {
                String topicName = topicDetails.getKey();
                Integer qos = topicDetails.getValue();

                messageStore.storeDisconnectMessage(topicName, channel.getProperty(MqttConstants
                        .CLIENT_ID_PROPERTY_NAME), isCleanSession, QOSLevel
                        .getQoSFromValue(qos));
            }
        } catch (ConnectorException e) {
            String error = "Error while disconnecting the subscription";
            log.error(error, e);
            throw new BrokerException(error, e);
        }

        return true;
    }

}
