package app.service.coder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.QuotedPrintableCodec;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CoderServiceImpl implements CoderService {

    private static final Pattern ENCODED_VALUE_PATTERN = Pattern.compile("%[0-9a-f]{2}|\\S",
            Pattern.CASE_INSENSITIVE);

    @Override
    public String coderSHA1(String password) {
        MessageDigest mDigest = null;

        try {
            mDigest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] result = mDigest.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    @Override
    public String encoderUtf8ToRfc5987(String str) throws UnsupportedEncodingException {
        final byte[] rawBytes = str.getBytes("UTF-8");
        final int len = rawBytes.length;
        final StringBuilder sb = new StringBuilder(len << 1);
        final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        final byte[] attributeChars = {'!', '#', '$', '&', '+', '-', '.', '0',
                '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
                'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '^', '_', '`', 'a',
                'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '|',
                '~'};
        for (final byte b : rawBytes) {
            if (Arrays.binarySearch(attributeChars, b) >= 0) {
                sb.append((char) b);
            } else {
                sb.append('%');
                sb.append(digits[0x0f & (b >>> 4)]);
                sb.append(digits[b & 0x0f]);
            }
        }
        return sb.toString();
    }

    @Override
    public String encoderRfc5987ToUtf8(String str) throws UnsupportedEncodingException{
        Matcher matcher = ENCODED_VALUE_PATTERN.matcher(str);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while (matcher.find()) {
            String matched = matcher.group();
            if (matched.startsWith("%")) {
                Integer value = Integer.parseInt(matched.substring(1), 16);
                bos.write(value);
                } else {
                bos.write(matched.charAt(0));
                }
            }
        return new String(bos.toByteArray(), "UTF-8");
    }

    @Override
    public String encoderBase64ToUtf8(String str) throws UnsupportedEncodingException {
        return new String(org.apache.commons.codec.binary.Base64.decodeBase64(str), "utf-8");
    }

    @Override
    public String encoderQuotedPrintableToUtf8(String str) throws DecoderException, UnsupportedEncodingException {
        return new String(QuotedPrintableCodec.decodeQuotedPrintable(str.getBytes()), "UTF-8");
    }

    @Override
    public String encoderUnique(String str, String incomingCoding, String outgoingCoding) throws UnsupportedEncodingException {
        return new String(str.getBytes(incomingCoding), outgoingCoding);
    }


}
