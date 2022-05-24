package com.extremex.kotex_libs;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;

import javax.crypto.EncryptedPrivateKeyInfo;

public class EncryptD extends EncryptedPrivateKeyInfo {
    public EncryptD(byte[] encoded) throws IOException {
        super(encoded);
    }

    public EncryptD(String algName, byte[] encryptedData) throws NoSuchAlgorithmException {
        super(algName, encryptedData);
    }

    public EncryptD(AlgorithmParameters algParams, byte[] encryptedData) throws NoSuchAlgorithmException {
        super(algParams, encryptedData);
    }
}
