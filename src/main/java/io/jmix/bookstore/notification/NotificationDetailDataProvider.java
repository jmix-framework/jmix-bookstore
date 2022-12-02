package io.jmix.bookstore.notification;

import java.util.List;

public interface NotificationDetailDataProvider<T extends InAppNotificationSource> {

    /**
     * checks if a given event is supported by this NotificationDetailDataProvider
     * @param event the event to check
     * @return true if supported, otherwise false
     */
    boolean supports(InAppNotificationSource event);

    /**
     * provides the data details for the to be sent notification of the given event
     * @param event the event to create notifications for
     * @return the notification detail data
     */
    NotificationDetailData provideData(T event);


    /**
     * Record representing detail data required to create a notification out of an event
     * @param recipients the recipients (usernames) to retrieve the notification
     * @param subject the subject to use for the notification
     * @param body the body of the plain text message for the notification
     */
    record NotificationDetailData(
            List<String> recipients,
            String subject,
            String body
    ){}

}
