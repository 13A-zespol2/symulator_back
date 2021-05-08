package psk.isi.simulator.model.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class NumberBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNumberBalance;
    @OneToOne
    private PhoneNumber phoneNumber;
    private Double balanceAccount;
    private Double balanceInternet;
    private int smsBalance;

    public NumberBalance(PhoneNumber phoneNumber, Double balanceAccount, Double balanceInternet, int smsBalance) {
        this.phoneNumber = phoneNumber;
        this.balanceAccount = balanceAccount;
        this.balanceInternet = balanceInternet;
        this.smsBalance = smsBalance;
    }
}
