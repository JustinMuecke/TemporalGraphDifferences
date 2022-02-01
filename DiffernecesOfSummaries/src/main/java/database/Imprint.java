package database;
import java.io.Serializable;
import java.util.Set;

public class Imprint implements Serializable {

    public int _id;
    public long _timestamp;
    public Set<String> _payload;
    public int _schemaElementID;



    public Imprint(int _id, long _timestamp, Set<String> _payload, int _schemaElementID) {
        this._id = _id;
        this._timestamp = _timestamp;
        this._payload = _payload;
        this._schemaElementID = _schemaElementID;
    }


    @Override
    public String toString() {
        return "Imprint{" +
                "_id=" + _id +
                ", _timestamp=" + _timestamp +
                ", _payload=" + _payload +
                ", _schemaElementID=" + _schemaElementID +
                '}';
    }

    public String toJson(){
        return "{ \n\"_id\" :" + _id + ",\n \"_timestamp \" : "+ _timestamp +",\n \" _payload\" : \"" + _payload + "\",\n \"_schemaElementID\" : " + _schemaElementID + "\n }\n" ;
    }
}

