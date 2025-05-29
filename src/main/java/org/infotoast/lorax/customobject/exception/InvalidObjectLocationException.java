package org.infotoast.lorax.customobject.exception;

import org.infotoast.lorax.Lorax;

public class InvalidObjectLocationException extends NumberFormatException {
    public InvalidObjectLocationException(String message) {
        super(message);
        Lorax.plugin.getServer().getPluginManager().disablePlugin(Lorax.plugin);
    }
}
