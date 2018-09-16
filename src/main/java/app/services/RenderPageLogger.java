package app.services;

import java.util.Date;

public class RenderPageLogger {
    public static long calculatePageRenderTime(long requestTime) {
        return new Date().getTime() - requestTime;
    }
}
