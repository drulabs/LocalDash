package org.drulabs.localdash.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import org.drulabs.localdash.ChatActivity;
import org.drulabs.localdash.R;
import org.drulabs.localdash.model.DeviceDTO;
import org.drulabs.localdash.notification.NotificationToast;
import org.drulabs.localdash.transfer.DataSender;

/**
 * Authored by KaushalD on 9/2/2016.
 */
public class DialogUtils {

    public static final int CODE_PICK_IMAGE = 21;

    public static AlertDialog getServiceSelectionDialog(final Activity activity, final DeviceDTO
            selectedDevice) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle(selectedDevice.getDeviceName());
        String[] types = {"Share image", "Chat"};
        alertDialog.setItems(types, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                switch (which) {
                    case 0:
                        Intent imagePicker = new Intent(Intent.ACTION_PICK);
                        imagePicker.setType("image/*");
                        activity.startActivityForResult(imagePicker, CODE_PICK_IMAGE);
                        break;
                    case 1:
                        DataSender.sendChatRequest(activity, selectedDevice.getIp
                                (), selectedDevice.getPort());
                        NotificationToast.showToast(activity, "chat request " +
                                "sent");
                        break;
                }
            }

        });

        return (alertDialog.create());
    }

    public static AlertDialog getChatRequestDialog(final Activity activity, final DeviceDTO requesterDevice) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

        String chatRequestTitle = activity.getString(R.string.chat_request_title);
        chatRequestTitle = String.format(chatRequestTitle, requesterDevice.getPlayerName() + "(" +
                requesterDevice.getDeviceName() + ")");
        alertDialog.setTitle(chatRequestTitle);
        String[] types = {"Accept", "Reject"};
        alertDialog.setItems(types, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                switch (which) {
                    //Request accepted
                    case 0:
                        openChatActivity(activity, requesterDevice);
                        NotificationToast.showToast(activity, "Chat request " +
                                "accepted");
                        DataSender.sendChatResponse(activity, requesterDevice.getIp(),
                                requesterDevice.getPort(), true);
                        break;
                    // Request rejected
                    case 1:
                        DataSender.sendChatResponse(activity, requesterDevice.getIp(),
                                requesterDevice.getPort(), false);
                        NotificationToast.showToast(activity, "Chat request " +
                                "rejected");
                        break;
                }
            }

        });

        return (alertDialog.create());
    }

    public static void openChatActivity(Activity activity, DeviceDTO device) {
        Intent chatIntent = new Intent(activity, ChatActivity.class);
        chatIntent.putExtra(ChatActivity.KEY_CHAT_IP, device.getIp());
        chatIntent.putExtra(ChatActivity.KEY_CHAT_PORT, device.getPort());
        chatIntent.putExtra(ChatActivity.KEY_CHATTING_WITH, device.getPlayerName());
        activity.startActivity(chatIntent);
    }
}
