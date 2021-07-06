package com.levcn.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * @author shishaobin
 */
public class EventBusUtils {
    private EventBusUtils() {
    }

    /**
     * 注册 EventBus
     */
    public static void register(Object subscriber) {
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(subscriber)) {
            eventBus.register(subscriber);
        }
    }

    /**
     * 解除注册 EventBus
     */
    public static void unregister(Object subscriber) {
        EventBus eventBus = EventBus.getDefault();
        if (eventBus.isRegistered(subscriber)) {
            eventBus.unregister(subscriber);
        }
    }

    /**
     * 发送事件消息
     */
    public static void post(EventMessage event) {
        EventBus.getDefault().post(event);
    }

    /**
     * 发送粘性事件消息
     */
    public static void postSticky(EventMessage event) {
        EventBus.getDefault().postSticky(event);
    }
}
