package com.example.demo;

import java.util.Collection;
import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;


@SpringBootApplication
public class ReservationServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}
	
	
	private final ReservationRepository reservationRepository;	
	
	

	
	public ReservationServiceApplication(ReservationRepository reservationRepository) {

		this.reservationRepository = reservationRepository;
	}



		@Override
		public void run(String... strings) throws Exception {
			Stream.of("Wolfgang,Sabine,Luise,Tabea".split(","))
			
			.forEach(name -> reservationRepository.save(new Reservation(name)));
			
			reservationRepository.findAll().forEach(System.out::println);
		}

	
	

}

@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation,Long> {
	@RestResource(path = "by-name")
	Collection<Reservation> findByReservationName(@Param("rn") String rn);

}


@Entity

class Reservation {

	public Reservation() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReservationName() {
		return reservationName;
	}

	public void setReservationName(String reservationName) {
		this.reservationName = reservationName;
	}

	@Id
	@GeneratedValue
	private Long id;

	private String reservationName;

	public Reservation(String rn) {
		this.reservationName = rn;
	}
}