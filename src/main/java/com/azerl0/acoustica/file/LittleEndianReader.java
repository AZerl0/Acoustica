package com.azerl0.acoustica.file;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class LittleEndianReader {

    private final InputStream input;

    public LittleEndianReader(InputStream input) {
        this.input = input;
    }

    public int readByte() throws IOException {
        int value = input.read();

        if (value == -1) {
            throw new IOException("Unexpected end of file");
        }

        return value;
    }

    public int readUnsignedShort() throws IOException {
        int b0 = readByte();
        int b1 = readByte();

        return b0 | (b1 << 8);
    }

    public short readShort() throws IOException {
        return (short) readUnsignedShort();
    }

    public int readInt() throws IOException {
        int b0 = readByte();
        int b1 = readByte();
        int b2 = readByte();
        int b3 = readByte();

        return b0
                | (b1 << 8)
                | (b2 << 16)
                | (b3 << 24);
    }

    public String readString() throws IOException {
        int length = readInt();

        if (length < 0) {
            throw new IOException("Invalid string length: " + length);
        }

        byte[] bytes = input.readNBytes(length);

        if (bytes.length != length) {
            throw new IOException("Unexpected end of file");
        }

        return new String(bytes, StandardCharsets.UTF_8);
    }
}