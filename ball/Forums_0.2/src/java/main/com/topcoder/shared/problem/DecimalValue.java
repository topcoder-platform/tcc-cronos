package com.topcoder.shared.problem;

import com.topcoder.shared.netCommon.CSReader;
import com.topcoder.shared.netCommon.CSWriter;

import java.io.IOException;
import java.io.ObjectStreamException;

public class DecimalValue extends DataValue {
    private double value;

    public DecimalValue() {
    }

    public DecimalValue(double value) {
        this.value = value;
    }

    public void parse(DataValueReader reader, DataType type)
            throws IOException, DataValueParseException {
        reader.skipWhitespace();

        int i = reader.read();
        boolean valid = false;
        long sign = 1;
        StringBuffer sb = new StringBuffer();

        value = 0;
        if ((char) i == '+' || (char) i == '-') {
            if ((char) i == '-')
                sign = -1;
            reader.skipWhitespace();
            i = reader.read();
        }
        
        int previousI = 0;

        while (i != -1 && (Character.isDigit((char)i) || i=='.' || i=='e' || i=='E' || ((previousI == 'e' || previousI == 'E') && (i =='-' || i =='+') ))) {
            previousI = i;
            sb.append((char) i);
            i = reader.read();
            valid = true;
            
        }
        reader.unread(i);

        try {
            value = Double.parseDouble(sb.toString());
        } catch (Exception e) {
            reader.exception("Invalid decimal format.");
        }

        value *= sign;
    }

    public void customWriteObject(CSWriter writer) throws IOException {
        writer.writeDouble(value);
    }

    public void customReadObject(CSReader reader) throws IOException,
            ObjectStreamException {
        value = reader.readDouble();
    }

    public String encode() {
        return String.valueOf(value);
    }

    public Object getValue() {
        return new Double(value);
    }
}
