/**
 *  Classe per serializzare e deserializzare l'oggetto Corso inserito nel db.
 **/
package com.unibo.progettosweng.client.model.Serializer;

import com.unibo.progettosweng.client.model.Corso;
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
        out.writeUTF(value.getDocente());
        out.writeUTF(value.getCodocente());
        out.writeBoolean(value.getEsameCreato());
    }

    @Override
    public Corso deserialize(DataInput2 input, int available) throws IOException {
        return new Corso(input.readUTF(),input.readUTF(),input.readUTF(),input.readUTF(),input.readUTF(), input.readUTF(),input.readUTF(), input.readBoolean());
    }

}