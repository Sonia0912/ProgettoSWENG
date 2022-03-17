package com.unibo.progettosweng.shared;

import com.unibo.progettosweng.shared.model.Esame;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;
import java.io.IOException;
import java.io.Serializable;

public class SerializerEsame implements Serializer<Esame>, Serializable {

    private static final long serialVersionUID = 2L;

    @Override
    public void serialize(DataOutput2 out, Esame value) throws IOException {
        out.writeUTF(value.getData());
        out.writeUTF(value.getOra());
        out.writeUTF(value.getDifficolta());
        out.writeUTF(value.getDifficolta());
        out.writeUTF(value.getNomeCorso());
    }

    @Override
    public Esame deserialize(DataInput2 input, int available) throws IOException {
        return new Esame(input.readUTF(),input.readUTF(),input.readUTF(),input.readUTF(),input.readUTF() );
    }
}