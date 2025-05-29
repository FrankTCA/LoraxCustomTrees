package org.infotoast.lorax.customobject.exception;

import org.bukkit.configuration.InvalidConfigurationException;
import org.infotoast.lorax.Lorax;

public class InvalidObjectCommandException extends InvalidConfigurationException {
  public InvalidObjectCommandException(String message) {
    super(message);
    Lorax.plugin.getServer().getPluginManager().disablePlugin(Lorax.plugin);
  }
}
