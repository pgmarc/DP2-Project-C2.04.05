/*
 * AdministratorDashboardRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.company.dashboard;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicum.PracticumSession;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyDashboardRepository extends AbstractRepository {

	@Query("SELECT company FROM Company company WHERE company.userAccount.id = :id")
	Company findCompanyByUserAccountId(int id);

	@Query("SELECT AVG(s.duration) FROM PracticumSession s WHERE s.practicum.company.id = :companyId")
	Double averageDurationOfPracticumSessions(int companyId);

	@Query("SELECT STDDEV(s.duration) FROM PracticumSession s WHERE s.practicum.company.id = :companyId")
	Double standardDeviationOfPracticumSessionsDuration(int companyId);

	@Query("SELECT MIN(s.duration) FROM PracticumSession s WHERE s.practicum.company.id = :companyId")
	Double minDurationPracticumSessions(int companyId);

	@Query("SELECT MAX(s.duration) FROM PracticumSession s WHERE s.practicum.company.id = :companyId")
	Double maxDurationPracticumSessions(int companyId);

	@Query("SELECT AVG(p.practicaPeriodLength) FROM Practicum p WHERE p.company.id = :companyId")
	Double averageDurationOfPracticum(int companyId);

	@Query("SELECT STDDEV(p.practicaPeriodLength) FROM Practicum p WHERE p.company.id = :companyId")
	Double standardDeviationOfPracticumDuration(int companyId);

	@Query("SELECT MIN(p.practicaPeriodLength) FROM Practicum p WHERE p.company.id = :companyId")
	Double minDurationPracticum(int companyId);

	@Query("SELECT MAX(p.practicaPeriodLength) FROM Practicum p WHERE p.company.id = :companyId")
	Double maxDurationPracticum(int companyId);

	@Query("SELECT p.id FROM Practicum p WHERE p.company.id = :companyId AND p.draftMode = FALSE")
	List<Integer> findPracticumIds(int companyId);

	@Query("SELECT s FROM PracticumSession s WHERE s.practicum.id = :practicumId AND s.startingDate <= :end AND s.endingDate >= :start")
	Collection<PracticumSession> findSessionsAndEdgeCases(int practicumId, Date start, Date end);

}
