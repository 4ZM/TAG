/**
 * Copyright (c) 2013 Anders Sundman <anders@4zm.org>
 * 
 * This file is part of TAG.
 * 
 * TAG is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * TAG is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with TAG.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.sparvnastet.tag;

import java.io.IOException;
import java.util.Arrays;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.widget.Switch;
import android.widget.Toast;

public class TagActivity extends Activity {

    private static final byte[][] A_KEYS = {
            { (byte) 0xfc, (byte) 0x00, (byte) 0x01, (byte) 0x87, (byte) 0x78, (byte) 0xf7 },
            { (byte) 0xfc, (byte) 0x00, (byte) 0x01, (byte) 0x87, (byte) 0x78, (byte) 0xf7 },
            { (byte) 0xfc, (byte) 0x00, (byte) 0x01, (byte) 0x87, (byte) 0x78, (byte) 0xf7 },
            { (byte) 0xfc, (byte) 0x00, (byte) 0x01, (byte) 0x87, (byte) 0x78, (byte) 0xf7 },
            { (byte) 0xa6, (byte) 0x45, (byte) 0x98, (byte) 0xa7, (byte) 0x74, (byte) 0x78 },
            { (byte) 0xa6, (byte) 0x45, (byte) 0x98, (byte) 0xa7, (byte) 0x74, (byte) 0x78 },
            { (byte) 0xa6, (byte) 0x45, (byte) 0x98, (byte) 0xa7, (byte) 0x74, (byte) 0x78 },
            { (byte) 0xa6, (byte) 0x45, (byte) 0x98, (byte) 0xa7, (byte) 0x74, (byte) 0x78 },
            { (byte) 0x26, (byte) 0x94, (byte) 0x0b, (byte) 0x21, (byte) 0xff, (byte) 0x5d },
            { (byte) 0x26, (byte) 0x94, (byte) 0x0b, (byte) 0x21, (byte) 0xff, (byte) 0x5d },
            { (byte) 0xa6, (byte) 0x45, (byte) 0x98, (byte) 0xa7, (byte) 0x74, (byte) 0x78 },
            { (byte) 0xa6, (byte) 0x45, (byte) 0x98, (byte) 0xa7, (byte) 0x74, (byte) 0x78 },
            { (byte) 0xa6, (byte) 0x45, (byte) 0x98, (byte) 0xa7, (byte) 0x74, (byte) 0x78 },
            { (byte) 0xa6, (byte) 0x45, (byte) 0x98, (byte) 0xa7, (byte) 0x74, (byte) 0x78 },
            { (byte) 0xa6, (byte) 0x45, (byte) 0x98, (byte) 0xa7, (byte) 0x74, (byte) 0x78 },
            { (byte) 0xa6, (byte) 0x45, (byte) 0x98, (byte) 0xa7, (byte) 0x74, (byte) 0x78 }, };

    private static final byte[][] B_KEYS = {
            { (byte) 0x00, (byte) 0x00, (byte) 0x0f, (byte) 0xfe, (byte) 0x24, (byte) 0x88 },
            { (byte) 0x00, (byte) 0x00, (byte) 0x0f, (byte) 0xfe, (byte) 0x24, (byte) 0x88 },
            { (byte) 0x00, (byte) 0x00, (byte) 0x0f, (byte) 0xfe, (byte) 0x24, (byte) 0x88 },
            { (byte) 0x00, (byte) 0x00, (byte) 0x0f, (byte) 0xfe, (byte) 0x24, (byte) 0x88 },
            { (byte) 0x5c, (byte) 0x59, (byte) 0x8c, (byte) 0x9c, (byte) 0x58, (byte) 0xb5 },
            { (byte) 0x5c, (byte) 0x59, (byte) 0x8c, (byte) 0x9c, (byte) 0x58, (byte) 0xb5 },
            { (byte) 0x5c, (byte) 0x59, (byte) 0x8c, (byte) 0x9c, (byte) 0x58, (byte) 0xb5 },
            { (byte) 0x5c, (byte) 0x59, (byte) 0x8c, (byte) 0x9c, (byte) 0x58, (byte) 0xb5 },
            { (byte) 0xe4, (byte) 0xd2, (byte) 0x77, (byte) 0x0a, (byte) 0x89, (byte) 0xbe },
            { (byte) 0xe4, (byte) 0xd2, (byte) 0x77, (byte) 0x0a, (byte) 0x89, (byte) 0xbe },
            { (byte) 0x5c, (byte) 0x59, (byte) 0x8c, (byte) 0x9c, (byte) 0x58, (byte) 0xb5 },
            { (byte) 0x5c, (byte) 0x59, (byte) 0x8c, (byte) 0x9c, (byte) 0x58, (byte) 0xb5 },
            { (byte) 0x5c, (byte) 0x59, (byte) 0x8c, (byte) 0x9c, (byte) 0x58, (byte) 0xb5 },
            { (byte) 0x5c, (byte) 0x59, (byte) 0x8c, (byte) 0x9c, (byte) 0x58, (byte) 0xb5 },
            { (byte) 0x5c, (byte) 0x59, (byte) 0x8c, (byte) 0x9c, (byte) 0x58, (byte) 0xb5 },
            { (byte) 0x5c, (byte) 0x59, (byte) 0x8c, (byte) 0x9c, (byte) 0x58, (byte) 0xb5 }, };

    private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;

    private DrawingView mDrawingView;
    private Switch mWriteMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_layout);

        mDrawingView = (DrawingView) findViewById(R.id.drawingView);
        mWriteMode = (Switch) findViewById(R.id.writeSwitch);
        mWriteMode.setChecked(false);

        mAdapter = NfcAdapter.getDefaultAdapter(this);

        // Setup foreground processing of NFC intents
        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter techFilter = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        mFilters = new IntentFilter[] { techFilter };
        mTechLists = new String[][] { new String[] { MifareClassic.class.getName() } };

        Intent intent = getIntent();
        resolveIntent(intent);
    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.i("TAG", "NFC: onNewIntent");
        setIntent(intent);
        resolveIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tag, menu);
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("TAG", "NFC: disableForegroundDispatch");
        if (mAdapter != null)
            mAdapter.disableForegroundDispatch(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("TAG", "NFC: enableForegroundDispatch");
        if (mAdapter != null)
            mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);
    }

    void resolveIntent(Intent intent) {
        Log.i("TAG", "resolveIntent action=" + intent.getAction());

        String action = intent.getAction();
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

            Parcelable tags = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tags == null) {
                Log.i("TAG", "resolveIntent: ParcelableExtra (tag) was null");
                return;
            }

            tagDetected((Tag) tags);
        }

        setIntent(null);
    }

    private void tagDetected(Tag tag) {

        final int GFX_CONTENT_TAG = 2;

        final int fgOffset = 1;
        final int colLen = 3;
        final int bgOffset = 4;
        final int gfxOffset = 7;
        final int gfxLen = 128;

        // Gfx content data format: [TYPE 1][FG 3][BG 3][GFX 128]

        MifareClassic mifareTag = MifareClassic.get(tag);
        if (mifareTag == null) {
            Log.i("TAG", "Unknown tag type found (not MifareClassic)");
            return;
        }

        byte[] data = readTag(mifareTag);
        if (!mWriteMode.isChecked()) {
            // Occupied tag, parse data.

            if (data[0] != GFX_CONTENT_TAG) {
                Toast.makeText(this, "Unrecognized content type", Toast.LENGTH_LONG).show();
                return;
            }

            byte[] gfx = Arrays.copyOfRange(data, gfxOffset, gfxOffset + gfxLen);
            mDrawingView.setData(gfx);

            Toast.makeText(this, "Tag Read", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isAllZero(data)) {
            // Clean tag, write to it

            // Zero all data
            Arrays.fill(data, (byte) 0);

            // Data type constant
            data[0] = GFX_CONTENT_TAG;

            // FG Color
            data[1] = (byte) 255;
            data[2] = (byte) 255;
            data[3] = (byte) 255;

            // BG Color
            data[4] = (byte) 0;
            data[5] = (byte) 0;
            data[6] = (byte) 0;

            // Pixels
            byte[] gfxData = mDrawingView.getData();
            for (int i = 0; i < gfxData.length; ++i)
                data[i + gfxOffset] = gfxData[i];

            // NFC write
            writeTag(mifareTag, data);

            Toast.makeText(this, "Tagged!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "It's allready tagged...", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isAllZero(byte[] data) {
        for (byte b : data)
            if (b != 0)
                return false;
        return true;
    }

    private static final int firstSector = 13;

    public byte[] readTag(MifareClassic tag) {
        // 3 Sectors with 3 blocks with 16 bytes.

        byte[] rawData = new byte[3 * 3 * 16];
        try {
            Log.i("TAG", "NFC Read Start");

            tag.connect();

            int sectorCount = tag.getSectorCount();
            if (sectorCount < 16) {
                Log.i("TAG", "Too few sectors");
                return null;
            }
            for (int s = firstSector; s < 16; ++s) {
                for (int b = 0; b < 3; ++b) {

                    tag.authenticateSectorWithKeyA(s, A_KEYS[s]);
                    int blockIndex = s * 4 + b;
                    byte[] block = tag.readBlock(blockIndex);

                    for (int i = 0; i < 16; ++i)
                        rawData[((s - firstSector) * 3 + b) * 16 + i] = block[i];

                }
            }

            tag.close();

            Log.i("TAG", "NFC Read Success");

        } catch (IOException e) {
            Log.e("TAG", "NFC Read IOException");
        }

        return rawData;
    }

    public void writeTag(MifareClassic tag, byte[] data) {
        try {
            Log.i("TAG", "NFC Write Start");

            tag.connect();

            int sectorCount = tag.getSectorCount();
            if (sectorCount < 16) {
                Log.i("TAG", "Too few sectors");
                return;
            }

            byte[] blockBuffer = new byte[16];

            for (int s = firstSector; s < 16; ++s) {
                for (int b = 0; b < 3; ++b) {

                    tag.authenticateSectorWithKeyB(s, B_KEYS[s]);
                    int blockIndex = s * 4 + b;

                    for (int i = 0; i < 16; ++i)
                        blockBuffer[i] = data[((s - firstSector) * 3 + b) * 16 + i];

                    tag.writeBlock(blockIndex, blockBuffer);
                }
            }

            tag.close();

            Log.i("TAG", "NFC Write Success");

        } catch (IOException e) {
            Log.e("TAG", "NFC Write IOException");
        }
    }
}
