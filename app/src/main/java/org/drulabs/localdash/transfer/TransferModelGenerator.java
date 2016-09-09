package org.drulabs.localdash.transfer;

import org.drulabs.localdash.model.ChatDTO;
import org.drulabs.localdash.model.DeviceDTO;

/**
 * Authored by KaushalD on 8/28/2016.
 */
public class TransferModelGenerator {

    public static ITransferable generateDeviceTransferModelRequest(DeviceDTO device) {
        TransferModel transferModel = new TransferModel(TransferConstants.CLIENT_DATA, TransferConstants.TYPE_REQUEST,
                device.toString());
        return transferModel;
    }

    public static ITransferable generateDeviceTransferModelResponse(DeviceDTO device) {
        TransferModel transferModel = new TransferModel(TransferConstants.CLIENT_DATA, TransferConstants.TYPE_RESPONSE,
                device.toString());
        return transferModel;
    }

    public static ITransferable generateDeviceTransferModelRequestWD(DeviceDTO device) {
        TransferModel transferModel = new TransferModel(TransferConstants.CLIENT_DATA_WD, TransferConstants.TYPE_REQUEST,
                device.toString());
        return transferModel;
    }

    public static ITransferable generateDeviceTransferModelResponseWD(DeviceDTO device) {
        TransferModel transferModel = new TransferModel(TransferConstants.CLIENT_DATA_WD, TransferConstants.TYPE_RESPONSE,
                device.toString());
        return transferModel;
    }

    public static ITransferable generateChatTransferModel(ChatDTO chat) {
        //All chats are type response as no further response is needed as of now
        TransferModel transferModel = new TransferModel(TransferConstants.CHAT_DATA,
                TransferConstants.TYPE_RESPONSE,
                chat.toString());
        return transferModel;
    }

    public static ITransferable generateChatRequestModel(DeviceDTO device) {
        TransferModel transferModel = new TransferModel(TransferConstants.CHAT_REQUEST_SENT,
                TransferConstants.TYPE_REQUEST, device.toString());
        return transferModel;
    }

    public static ITransferable generateChatResponseModel(DeviceDTO device, boolean
            isChatRequestAccepted) {
        int reqCode = isChatRequestAccepted ? TransferConstants.CHAT_REQUEST_ACCEPTED :
                TransferConstants.CHAT_REQUEST_REJECTED;
        TransferModel transferModel = new TransferModel(reqCode,
                TransferConstants.TYPE_RESPONSE, device.toString());
        return transferModel;
    }

    static class TransferModel implements ITransferable {

        int reqCode;
        String reqType;
        String data;

        TransferModel(int reqCode, String reqType, String data) {
            this.reqCode = reqCode;
            this.reqType = reqType;
            this.data = data;
        }

        @Override
        public int getRequestCode() {
            return reqCode;
        }

        @Override
        public String getRequestType() {
            return reqType;
        }

        @Override
        public String getData() {
            return data;
        }
    }
}
