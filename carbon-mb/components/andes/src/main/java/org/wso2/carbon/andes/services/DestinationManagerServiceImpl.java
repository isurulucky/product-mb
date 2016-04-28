/*
 * Copyright (c) 2016, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.andes.services;

import org.wso2.carbon.andes.services.beans.DestinationManagementBeans;
import org.wso2.carbon.andes.services.exceptions.DestinationManagerException;
import org.wso2.carbon.andes.services.types.Destination;
import org.wso2.carbon.andes.services.types.DestinationRolePermission;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This implementation provides the base for managing all messages related services.
 */
public class DestinationManagerServiceImpl implements DestinationManagerService {
    private static Set<DestinationRolePermission> destinationRolePermissions = new HashSet<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Destination> getDestinations(String protocol, String destinationType, String keyword,
                                             int offset, int limit) throws DestinationManagerException {
        return DestinationManagementBeans.getInstance().getDestinations(protocol, destinationType, keyword, offset,
                                                                                                                limit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteDestinations(String protocol, String destinationType) throws DestinationManagerException {
        DestinationManagementBeans.getInstance().deleteDestinations(protocol, destinationType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Destination getDestination(String protocol, String destinationType, String
            destinationName) throws DestinationManagerException {
        return DestinationManagementBeans.getInstance().getDestination(protocol, destinationType, destinationName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Destination createDestination(String protocol, String destinationType, String
            destinationName) throws DestinationManagerException {
        String currentUsername = "admin";
        return DestinationManagementBeans.getInstance().createDestination(protocol, destinationType, destinationName,
                                                                                                    currentUsername);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<DestinationRolePermission> getDestinationPermission(String protocol, String destinationType,
                                                                    String destinationName) {
        destinationRolePermissions.add(new DestinationRolePermission("admin-role", true, true));
        return destinationRolePermissions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DestinationRolePermission updateDestinationPermissions(
            String protocol, String destinationType,
            String destinationName,
            DestinationRolePermission destinationRolePermission) {
        destinationRolePermissions.add(destinationRolePermission);
        // Get permissions for the destination.
        // Update the permissions for the destination.
        // Return the new list of permissions.
        return new DestinationRolePermission("admin-role", true, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteDestination(String protocol, String destinationType, String destinationName)
                                                                                throws DestinationManagerException {
        DestinationManagementBeans.getInstance().deleteDestination(protocol, destinationType, destinationName);
    }
}