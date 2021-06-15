package psk.isi.simulator.model.transport.converter;

import psk.isi.simulator.model.database.entities.PhoneNumber;
import psk.isi.simulator.model.database.entities.SmsHistory;
import psk.isi.simulator.model.transport.dto.SmsHistoryDTO;


/**
 * Klasa odpowiedzialna za konwersję obiektów SmsHistory
 */
public class SmsHistoryConverter {
    /**
     * Metoda odpowiedzialna za konwersję obiektu klasy SmsHistoryDTO do obiektu klasy SmsHistory.
     */
    public static SmsHistory toEntity(SmsHistoryDTO smsHistoryDTO, PhoneNumber phoneNumber){
        PhoneNumber phoneNumber1 = PhoneNumberConverter.toEntity(smsHistoryDTO.getPhoneNumberSender());

        return new SmsHistory(null ,phoneNumber1,phoneNumber,smsHistoryDTO.getMessage(), smsHistoryDTO.getDateSms());
    }
    /**
     * Metoda odpowiedzialna za konwersję obiektu klasy SmsHistory do obiektu klasy SmsHistoryDTO.
     */
    public static SmsHistoryDTO toDto(SmsHistory smsHistory){
        return new SmsHistoryDTO(PhoneNumberConverter.toDto(smsHistory.getPhoneNumberSender()) , smsHistory.getPhoneNumberReceiver().getNumber(), smsHistory.getMessage(), smsHistory.getDateSms());
    }
}
