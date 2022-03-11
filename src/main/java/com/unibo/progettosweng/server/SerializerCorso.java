package com.unibo.progettosweng.server;

import com.unibo.progettosweng.model.Corso;
import com.unibo.progettosweng.model.Esame;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

import java.io.IOException;
import java.io.Serializable;

public class SerializerCorso implements Serializer<Corso>, Serializable {

    private static final long serialVersionUID = 3L;

    @Override
    public void serialize(DataOutput2 out, Corso value) throws IOException {
        out.writeUTF(value.getNomeCorso());
        out.writeUTF(value.getDataInizio());
        out.writeUTF(value.getDataFine());
        out.writeUTF(value.getDescrizione());
        out.writeUTF(value.getDipartimento());

    }

    @Override
    public Corso deserialize(DataInput2 input, int available) throws IOException {
        return new Corso(input.readUTF(),input.readUTF(),input.readUTF(),input.readUTF(), input.readUTF() );
    }
}