package com.dentalclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dentalclinic.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long patientId);
    
    @Query("SELECT a FROM Appointment a WHERE DATE(a.appointmentDateTime) = CURRENT_DATE")
    List<Appointment> findAppointmentsToday();

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId")
    List<Appointment> findByDoctorId(Long doctorId);

    @Query("SELECT a FROM Appointment a WHERE DATE(a.appointmentDateTime) = CURRENT_DATE AND a.doctor.id = :doctorId")
    List<Appointment> findAppointmentsTodayByDoctorId(Long doctorId);

}
