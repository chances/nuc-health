package nucchallenge.utils;

import java.io.FileReader;
import org.json.simple.JSONObject;import org.json.simple.parser.JSONParser;

public class ConfigManager {
    private String pulseOxSerialTTY;
    private String bpcSerialTTY;
    private String patientCSV;
    private String psqlHost;
    private String psqlUser;
    private String psqlPasswd;

    ConfigManager() {
        this.pulseOxSerialTTY = null;
        this.bpcSerialTTY = null;
        this.patientCSV = null;
        this.psqlHost = null;
        this.psqlUser = null;
        this.psqlPasswd = null;
    }

    ConfigManager(String jsonFile) {
        JSONParser jsonParser = new JSONParser();

        try {
            Object object = jsonParser.parse(new FileReader(System.getProperty("user.dir") + "/src/main/config/" + jsonFile));
            JSONObject jsonObject = (JSONObject) object;

            this.pulseOxSerialTTY = (String) jsonObject.get("PulseOxTTY");
            this.bpcSerialTTY = (String) jsonObject.get("BpcTTY");
            this.patientCSV = (String) jsonObject.get("PatientCSV");
            this.psqlHost = (String) jsonObject.get("PsqlHostAddress");
            this.psqlUser = (String) jsonObject.get("PsqlUser");
            this.psqlPasswd = (String) jsonObject.get("PsqlPasswd");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void getConfigurations(String jsonFile) {

       JSONParser jsonParser = new JSONParser();

        try {
            Object object = jsonParser.parse(new FileReader(System.getProperty("user.dir") + "/src/main/config/" + jsonFile));
            JSONObject jsonObject = (JSONObject) object;

            this.pulseOxSerialTTY = (String) jsonObject.get("PulseOxTTY");
            this.bpcSerialTTY = (String) jsonObject.get("BpcTTY");
            this.patientCSV = (String) jsonObject.get("PatientCSV");
            this.psqlHost = (String) jsonObject.get("PsqlHostAddress");
            this.psqlUser = (String) jsonObject.get("PsqlUser");
            this.psqlPasswd = (String) jsonObject.get("PsqlPasswd");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String getPulseOxSerialTTY() {
        return pulseOxSerialTTY;
    }

    public String getBpcSerialTTY() {
        return bpcSerialTTY;
    }

    public String getPatientCSV() {
        return patientCSV;
    }

    public String getPsqlHost() {
        return psqlHost;
    }

    public String getPsqlUser() {
        return psqlUser;
    }

    public String getPsqlPasswd() {
        return psqlPasswd;
    }
}
