package com.murat.getallprojects;

/**
 * Created by murat on 8.07.2017.
 */

public class Donusturucu {
    public String dbToString(String ozelGun) {

        switch (ozelGun) {
            case "Regaib_Kandili":
                return "REGAİB KANDİLİ MESAJLARI";

            case "Berat_Kandili":
                return "BERAAT KANDİLİ MESAJLARI";

            case "Kadir_Gecesi":
                return "KADİR GECESİ MESAJLARI";

            case "Kurban_Bayram":
                return "KURBAN BAYRAMI MESAJLARI";

            case "Mevlid_Kandili":
                return "MEVLİD KANDİLİ MESAJLARI";

            case "Ramazan_Bayramı":
                return "RAMAZAN BAYRAMI MESAJLARI";

            case "Mirac_Kandili":
                return "MİRAÇ KANDİLİ MESAJLARI";

            case "Kutlu_Dogum":
                return "KUTLU DOĞUM HAFTASI MESAJLARI";

            case "Anneler_Gunu":
                return "ANNELER GÜNÜ MESAJLARI";

            case "Cuma_Mesajlari":
                return "CUMA MESAJLARI";

            case "Babalar_gunu":
                return "BABALAR GÜNÜ MESAJLARI ";

            case "Sevgili_sozleri":
                return "SEVGİ & AŞK SÖZLERİ";
        }
        return ozelGun;
    }

    public String stringToDb(String ozelGun) {
        switch (ozelGun) {
            case "REGAİB KANDİLİ MESAJLARI":
                return "Regaib_Kandili";

            case "SEVGİ & AŞK SÖZLERİ":
                return "Sevgili_sozleri";

            case "BABALAR GÜNÜ MESAJLARI ":
                return "Babalar_gunu";

            case "KUTLU DOĞUM HAFTASI MESAJLARI":
                return "Kutlu_Dogum";

            case "BERAAT KANDİLİ MESAJLARI":
                return "Berat_Kandili";

            case "KADİR GECESİ MESAJLARI":
                return "Kadir_Gecesi";

            case "KURBAN BAYRAMI MESAJLARI":
                return "Kurban_Bayram";

            case "MEVLİD KANDİLİ MESAJLARI":
                return "Mevlid_Kandili";

            case "RAMAZAN BAYRAMI MESAJLARI":
                return "Ramazan_Bayramı";

            case "MİRAÇ KANDİLİ MESAJLARI":
                return "Mirac_Kandili";

            case "ANNELER GÜNÜ MESAJLARI":
                return "Anneler_Gunu";

            case "BABALAR GÜNÜ MESAJLARI":
                return "Babalar_gunu";
            case "CUMA MESAJLARI":
                return "Cuma_Mesajlari";
            default:
                break;
        }
        return ozelGun;
    }
}