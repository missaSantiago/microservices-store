package micro.proyect.customer.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.io.Serializable;

@Entity
@Table(name = "customers")
@Data
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El número de documento no puede estar vacío")
    @Size(min = 8, max = 8, message = "El tamaño del número de documento es de 8")
    @Column(name = "number_id", unique = true, length = 8, nullable = false)
    private String numberID;

    @NotEmpty(message = "El nombre no puede estar vacío")
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty(message = "El apellido no puede estar vacío")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotEmpty(message = "El Email no puede estar vacío")
    @Email(message = "Esta no es un email válido")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "photo_url")
    private String photoUrl;

    @NotNull(message = "La región no puede estar vacía")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "hanlder"})
    private Region region;

    private String state;
}
