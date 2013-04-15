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
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

    final static byte[] cookie = new byte[] { 0x02, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x00, 0x00, 0x00 };
    final static int cookieOffset = 0;
    final static int gfxOffset = 7;
    final static int gfxLen = 128;

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

        // Setup accel for shake detection
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

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
        Log.i("TAG", "NFC: disableForegroundDispatch");
        if (mAdapter != null)
            mAdapter.disableForegroundDispatch(this);

        if (mSensorManager != null)
            mSensorManager.unregisterListener(mSensorListener);

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("TAG", "NFC: enableForegroundDispatch");
        if (mAdapter != null)
            mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);

        if (mSensorManager != null)
            mSensorManager.registerListener(mSensorListener,
                    mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
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

        // MAGIC COOKIE: 02 FF FF FF 00 00 00
        // Content format: [MAGIC COOKIE 7][GFX 128] = 135
        MifareClassic mifareTag = MifareClassic.get(tag);
        if (mifareTag == null) {
            Log.i("TAG", "Unknown tag type found (not MifareClassic)");
            return;
        }

        // Read the tag
        byte[] data = readTag(mifareTag);
        if (data == null) {
            Toast.makeText(this, "Unrecognized Card", Toast.LENGTH_SHORT).show();
            return;
        }

        // Sanity check. Bail out if it contains data but no cookie
        if (!isSpaceUnused(data) && !isCookiePresent(data)) {
            Toast.makeText(this, "Card doesn't have any unused space.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Read mode, process
        if (!mWriteMode.isChecked()) {
            processRead(data);
        } else {
            processWrite(mifareTag, data);
        }
    }

    private void processRead(byte[] data) {
        if (isSpaceUnused(data)) {
            Toast.makeText(this, "Card is not tagged.", Toast.LENGTH_SHORT).show();
            return;
        }

        byte[] gfx = Arrays.copyOfRange(data, gfxOffset, gfxOffset + gfxLen);
        mDrawingView.setData(gfx);

        Toast.makeText(this, "Tag Read!", Toast.LENGTH_SHORT).show();
    }

    private void processWrite(MifareClassic mifareTag, byte[] data) {
        // Zero all data
        Arrays.fill(data, (byte) 0);

        // Write magic cookie
        for (int i = 0; i < cookie.length; ++i)
            data[i] = cookie[i];

        // Write Pixels
        byte[] gfxData = mDrawingView.getData();
        for (int i = 0; i < gfxData.length; ++i)
            data[i + gfxOffset] = gfxData[i];

        // NFC write
        writeTag(mifareTag, data);

        Toast.makeText(this, "Tagged!", Toast.LENGTH_SHORT).show();
        mWriteMode.setChecked(false);
    }

    private boolean isSpaceUnused(byte[] data) {
        return isAllX(data, (byte) 0) || isAllX(data, (byte) 0xff);
    }

    private boolean isCookiePresent(byte[] data) {
        if (data == null || data.length < cookie.length)
            return false;

        for (int i = 0; i < cookie.length; ++i) {
            if (data[i] != cookie[i])
                return false;
        }

        return true;
    }

    private boolean isAllX(byte[] data, byte x) {
        for (byte b : data)
            if (b != x)
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
            return null;
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

    // Shake code from:
    // http://stackoverflow.com/questions/2317428/android-i-want-to-shake-it
    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter

            if (mAccel > 11) {
                byte[] data = new byte[128];
                mDrawingView.setData(data);
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
}
