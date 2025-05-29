package org.infotoast.lorax.customobject.exception;

import org.infotoast.lorax.Lorax;

public class CheckMissedException extends RuntimeException {
    public CheckMissedException(String message) {
        super(message);
        Lorax.plugin.getServer().getPluginManager().disablePlugin(Lorax.plugin);
    }
}
