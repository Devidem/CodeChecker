package Inp_Out.Interfaces;

import java.io.IOException;

public interface FileArrayer {
    public String[][] fileToArray (String In_File, int sheet) throws IOException;
}
