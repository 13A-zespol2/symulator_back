package psk.isi.simulator.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psk.isi.simulator.errors.NoSuchPhoneNumber;
import psk.isi.simulator.model.database.entities.PhoneNumber;
import psk.isi.simulator.model.database.entities.SmsHistory;
import psk.isi.simulator.model.database.repository.PhoneNumberRepository;
import psk.isi.simulator.model.database.repository.SmsHistoryRepository;
import psk.isi.simulator.model.transport.converter.SmsHistoryConverter;
import psk.isi.simulator.model.transport.dto.SmsHistoryDTO;
import psk.isi.simulator.service.SendMessageService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sms")
public class PhoneMessageController {

    private SendMessageService sendMessageService;

    private PhoneNumberRepository phoneNumberRepository;

    private SmsHistoryRepository smsHistoryRepository;

    @Autowired
    public PhoneMessageController(SendMessageService sendMessageService, PhoneNumberRepository phoneNumberRepository, SmsHistoryRepository smsHistoryRepository) {
        this.sendMessageService = sendMessageService;
        this.phoneNumberRepository = phoneNumberRepository;
        this.smsHistoryRepository = smsHistoryRepository;
    }

    @PostMapping(path = "/singlemessage")
    public ResponseEntity<SmsHistoryDTO> saveMessage(@RequestBody SmsHistoryDTO smsHistoryDTO) {
        SmsHistory smsHistory1;
        try {
            smsHistory1 = sendMessageService.saveMessage(smsHistoryDTO);

            return ResponseEntity.ok(SmsHistoryConverter.toDto(smsHistory1));

        } catch (NoSuchPhoneNumber noSuchPhoneNumber) {

            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/{phoneNumber}")
    public ResponseEntity<List<SmsHistoryDTO>> getAllNumber(@PathVariable String phoneNumber) {
        Optional<PhoneNumber> byNumber = phoneNumberRepository.findByNumber(phoneNumber);
        List<SmsHistory> collect = smsHistoryRepository.findAllByPhoneNumberSender(byNumber.get());

        List<SmsHistoryDTO> collect1 = collect.stream()
                .sorted(Comparator.comparing(SmsHistory::getDateSms).reversed())
                .filter(distinctByKey(SmsHistory::getPhoneNumberReceiver))
                .map(SmsHistoryConverter::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(collect1);
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
