package songs;

/**
 * Created by Daniel on 2014-09-18.
 */
public class Notes {
    public static final double A2  = 110;
    public static final double Bb2 = 116.54;
    public static final double B2  = 123.47;
    public static final double C3  = 130.81;
    public static final double Db3 = 138.59;
    public static final double D3  = 146.83;
    public static final double Eb3 = 155.56;
    public static final double E3  = 164.81;
    public static final double F3  = 174.61;
    public static final double Gb3 = 185;
    public static final double G3  = 196;
    public static final double Ab3 = 207.65;
    public static final double A3  = 220;
    public static final double Bb3 = 233.08;
    public static final double B3  = 246.94;
    public static final double C4  = 261.63;
    public static final double Db4 = 277.18;
    public static final double D4  = 293.66;
    public static final double Eb4 = 311.13;
    public static final double E4  = 329.63;
    public static final double F4  = 349.23;
    public static final double Gb4 = 369.99;
    public static final double G4  = 392;
    public static final double Ab4 = 415.30;
    public static final double A4  = 440;
    public static final double Bb4 = 493.88;
    public static final double B4  = 493.88;
    public static final double C5  = 523.25;
    public static final double Db5 = 277.18*2; //I got lazy around here
    public static final double D5  = 293.66*2;
    public static final double Eb5 = 311.13*2;
    public static final double E5  = 329.63*2;
    public static final double F5  = 349.23*2;
    public static final double Gb5 = 369.99*2;
    public static final double G5  = 392*2;
    public static final double Ab5 = 415.30*2;
    public static final double A5  = 440*2;
    public static final double Bb5 = 493.88*2;
    public static final double B5  = 493.88*2;
    public static final double C6  = 523.25*2;

    public Notes(){}

    public String notesToString(double[] notes){
        String ret = "";
        for (double note : notes)
            ret += noteToString(note) + " ";
        return ret.trim();
    }

    public double[] StringToNotes(String notes){
        notes = notes.trim();
        String[] str = notes.split(" ");
        double[] ret = new double[str.length];

        double note;
        for (int i = 0; i < str.length; i++){
            note = stringToNote(str[i]);
            if (note > 0) {
                ret[i] = note;
            }
            else{
                System.out.println("String to note parse error: " + str[i]);
                break;
            }

        }
        return ret;
    }

    public String noteToString(double note){
        if      (note == A2)  return "A2";
        else if (note == Bb2) return "Bb2";
        else if (note == B2)  return "B2";
        else if (note == C3)  return "C3";
        else if (note == Db3) return "Db3";
        else if (note == D3)  return "D3";
        else if (note == Eb3) return "Eb3";
        else if (note == E3)  return "E3";
        else if (note == F3)  return "F3";
        else if (note == Gb3) return "Gb3";
        else if (note == G3)  return "G3";
        else if (note == Ab3) return "Ab3";
        else if (note == A3)  return "A3";
        else if (note == Bb3) return "Bb3";
        else if (note == B3)  return "B3";
        else if (note == C4)  return "C4";
        else if (note == Db4) return "Db4";
        else if (note == D4)  return "D4";
        else if (note == Eb4) return "Eb4";
        else if (note == E4)  return "E4";
        else if (note == F4)  return "F4";
        else if (note == Gb4) return "Gb4";
        else if (note == G4)  return "G4";
        else if (note == Ab4) return "Ab4";
        else if (note == A4)  return "A4";
        else if (note == Bb4) return "Bb4";
        else if (note == B4)  return "B4";
        else if (note == C5)  return "C5";
        else if (note == Db5) return "Db5";
        else if (note == D5)  return "D5";
        else if (note == Eb5) return "Eb5";
        else if (note == E5)  return "E5";
        else if (note == F5)  return "F5";
        else if (note == Gb5) return "Gb5";
        else if (note == G5)  return "G5";
        else if (note == Ab5) return "Ab5";
        else if (note == A5)  return "A5";
        else if (note == Bb5) return "Bb5";
        else if (note == B5)  return "B5";
        else if (note == C5)  return "C5";
        else return "ERROR";
    }

    public double stringToNote(String note){
        note = note.trim();
        if      (note.equalsIgnoreCase("A2"))  return A2;
        else if (note.equalsIgnoreCase("Bb2")) return Bb2;
        else if (note.equalsIgnoreCase("B2"))  return B2;
        else if (note.equalsIgnoreCase("C3"))  return C3;
        else if (note.equalsIgnoreCase("Db3")) return Db3;
        else if (note.equalsIgnoreCase("D3"))  return D3;
        else if (note.equalsIgnoreCase("Eb3")) return Eb3;
        else if (note.equalsIgnoreCase("E3"))  return E3;
        else if (note.equalsIgnoreCase("F3"))  return F3;
        else if (note.equalsIgnoreCase("Gb3")) return Gb3;
        else if (note.equalsIgnoreCase("G3"))  return G3;
        else if (note.equalsIgnoreCase("Ab3")) return Ab3;
        else if (note.equalsIgnoreCase("A3"))  return A3;
        else if (note.equalsIgnoreCase("Bb3")) return Bb3;
        else if (note.equalsIgnoreCase("B3"))  return B3;
        else if (note.equalsIgnoreCase("C4"))  return C4;
        else if (note.equalsIgnoreCase("Db4")) return Db4;
        else if (note.equalsIgnoreCase("D4"))  return D4;
        else if (note.equalsIgnoreCase("Eb4")) return Eb4;
        else if (note.equalsIgnoreCase("E4"))  return E4;
        else if (note.equalsIgnoreCase("F4"))  return F4;
        else if (note.equalsIgnoreCase("Gb4")) return Gb4;
        else if (note.equalsIgnoreCase("G4"))  return G4;
        else if (note.equalsIgnoreCase("Ab4")) return Ab4;
        else if (note.equalsIgnoreCase("A4"))  return A4;
        else if (note.equalsIgnoreCase("Bb4")) return Bb4;
        else if (note.equalsIgnoreCase("B4"))  return B4;
        else if (note.equalsIgnoreCase("C5"))  return C5;
        else if (note.equalsIgnoreCase("Db5")) return Db5;
        else if (note.equalsIgnoreCase("D5"))  return D5;
        else if (note.equalsIgnoreCase("Eb5")) return Eb5;
        else if (note.equalsIgnoreCase("E5"))  return E5;
        else if (note.equalsIgnoreCase("F5"))  return F5;
        else if (note.equalsIgnoreCase("Gb5")) return Gb5;
        else if (note.equalsIgnoreCase("G5"))  return G5;
        else if (note.equalsIgnoreCase("Ab5")) return Ab5;
        else if (note.equalsIgnoreCase("A5"))  return A5;
        else if (note.equalsIgnoreCase("Bb5")) return Bb5;
        else if (note.equalsIgnoreCase("B5"))  return B5;
        else if (note.equalsIgnoreCase("C5"))  return C5;
        else return -1;
    }
}
