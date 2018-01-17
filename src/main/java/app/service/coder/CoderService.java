package app.service.coder;


import org.apache.commons.codec.DecoderException;

import java.io.UnsupportedEncodingException;

public interface CoderService {
    String coderSHA1(String password);
    String encoderUtf8ToRfc5987(String str) throws UnsupportedEncodingException;
    String encoderRfc5987ToUtf8(String str) throws UnsupportedEncodingException;
    String encoderBase64ToUtf8(String str) throws UnsupportedEncodingException;
    String encoderQuotedPrintableToUtf8(String str) throws DecoderException, UnsupportedEncodingException;
    String encoderUnique(String str, String incomingCoding, String outgoingCoding) throws UnsupportedEncodingException;

}
