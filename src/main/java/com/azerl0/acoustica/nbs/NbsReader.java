package com.azerl0.acoustica.nbs;

import com.azerl0.acoustica.file.LittleEndianReader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class NbsReader {

    public static NbsSong read(Path path) throws IOException {

        try (InputStream input = Files.newInputStream(path)) {

            LittleEndianReader reader =
                    new LittleEndianReader(input);

            int zero = reader.readUnsignedShort();

            int version = reader.readByte();

            int vanillaInstrumentCount =
                    reader.readByte();

            int songLength =
                    reader.readUnsignedShort();

            int layerCount =
                    reader.readUnsignedShort();

            String name =
                    reader.readString();

            String author =
                    reader.readString();

            String originalAuthor =
                    reader.readString();

            String description =
                    reader.readString();

            int tempoRaw =
                    reader.readUnsignedShort();

            double tempo =
                    tempoRaw / 100.0;

            boolean autoSaving =
                    reader.readByte() == 1;

            int autoSavingDuration =
                    reader.readByte();

            int timeSignature =
                    reader.readByte();

            int minutesSpent =
                    reader.readInt();

            int leftClicks =
                    reader.readInt();

            int rightClicks =
                    reader.readInt();

            int noteBlocksAdded =
                    reader.readInt();

            int noteBlocksRemoved =
                    reader.readInt();

            String midiFineName =
                    reader.readString();

            boolean loop =
                    reader.readByte() == 1;

            int maxLoopCount =
                    reader.readByte();

            int loopStartTick =
                    reader.readUnsignedShort();

            NbsSongMeta meta = new NbsSongMeta(
                    version,
                    vanillaInstrumentCount,
                    songLength,
                    layerCount,
                    name,
                    author,
                    originalAuthor,
                    description,
                    tempo,
                    autoSaving,
                    autoSavingDuration,
                    timeSignature,
                    minutesSpent,
                    leftClicks,
                    rightClicks,
                    noteBlocksAdded,
                    noteBlocksRemoved,
                    midiFineName,
                    loop,
                    maxLoopCount,
                    loopStartTick
            );


            // ========================
            // PART 2
            // ========================

            List<NbsNote> notes = new ArrayList<>();

            int tick = -1;

            while (true) {

                int tickJump =
                        reader.readUnsignedShort();

                if (tickJump == 0) {
                    break;
                }

                tick += tickJump;

                int layer = -1;

                while (true) {

                    int layerJump =
                            reader.readUnsignedShort();

                    if (layerJump == 0) {
                        break;
                    }

                    layer += layerJump;

                    int instrument =
                            reader.readByte();

                    int key =
                            reader.readByte();

                    int velocity =
                            reader.readByte();

                    int panning =
                            reader.readByte();

                    short pitch =
                            reader.readShort();

                    notes.add(new NbsNote(
                            tick,
                            layer,
                            instrument,
                            key,
                            velocity,
                            panning,
                            pitch
                    ));
                }
            }


            // ========================
            // PART 3
            // ========================

            List<NbsLayer> layers =
                    new ArrayList<>();

           if (reader.hasRemaining()) {
               for (int i = 0; i < layerCount; i++) {

                   String layerName =
                           reader.readString();

                   boolean locked =
                           reader.readByte() == 1;

                   int volume =
                           reader.readByte();

                   int stereo =
                           reader.readByte();

                   layers.set(i, new NbsLayer(layerName, locked, volume, stereo));
               }
           }


            // ========================
            // PART 4
            // ========================

            List<NbsCustomInstrument> customInstruments =
                    new ArrayList<>();

            if (reader.hasRemaining()) {

                int count =
                        reader.readByte();

                for (int i = 0; i < count; i++) {

                    String instrumentName =
                            reader.readString();

                    String soundFile =
                            reader.readString();

                    int key =
                            reader.readByte();

                    boolean press =
                            reader.readByte() == 1;

                    customInstruments.add(
                            new NbsCustomInstrument(
                                    i,
                                    instrumentName,
                                    soundFile,
                                    key,
                                    press
                            )
                    );
                }
            }
            return new NbsSong(
                    meta,
                    notes,
                    layers,
                    customInstruments
            );
        }
    }
}