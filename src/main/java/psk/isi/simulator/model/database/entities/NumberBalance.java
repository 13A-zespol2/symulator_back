package psk.isi.simulator.model.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
/**
 * Klasa odpowiadająca za tabelę ,,NumberBalance" (stan wykorzystania pakietów użytkownika).
 */
public class NumberBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNumberBalance;
    @OneToOne
    private PhoneNumber phoneNumber;
    private Double balanceAccount;
    private Double balanceInternet;
    private Double balanceMinutes;
    private int smsBalance;

    public NumberBalance(PhoneNumber phoneNumber, Double balanceAccount, Double balanceInternet, Double balanceMinutes ,int smsBalance) {
        this.phoneNumber = phoneNumber;
        this.balanceAccount = balanceAccount;
        this.balanceInternet = balanceInternet;
        this.balanceMinutes = balanceMinutes;
        this.smsBalance = smsBalance;
    }
}
