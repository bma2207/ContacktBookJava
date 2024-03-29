package com.axelor.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.axelor.domains.ContactDetails;

public class ContactServiceImpl implements ContactService {

	public EntityManager getConnection() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
		EntityManager em = emf.createEntityManager();
		return em;
	}

	@Override
	public void addContact(String fullname, String mobileno) {

		EntityManager entityManager = getConnection();

		entityManager.getTransaction().begin();

		ContactDetails contactDetails = new ContactDetails();
		contactDetails.setFullName(fullname);
		contactDetails.setMobileNo(mobileno);

		entityManager.persist(contactDetails);

		entityManager.getTransaction().commit();

	}

	@Override
	public List<ContactDetails> getAllcontacts() {

		EntityManager entityManager = getConnection();

		Query query = entityManager.createQuery("from ContactDetails");

		List contactList = query.getResultList();

		return contactList;
	}

	@Override
	public void deleteContact(int id) {
		EntityManager entityManager = getConnection();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("delete from ContactDetails where cid =:id");
		query.setParameter("id", id);
		query.executeUpdate();
		entityManager.getTransaction().commit();

	}

	@Override
	public ContactDetails getContactDetailsById(int id) {

		EntityManager entityManager = getConnection();
		
		Query query = entityManager.createQuery("from ContactDetails where cid =:id");
		query.setParameter("id", id);
		ContactDetails contactDetails = (ContactDetails) query.getSingleResult();

		return contactDetails;
	}

	@Override
	public void updateContact(int cid, String fullname, String mobileno) {

		EntityManager entityManager = getConnection();
		entityManager.getTransaction().begin();

		Query query = entityManager
				.createQuery("update ContactDetails set fullName=:fullname , mobileNo =:mobileno where cid=:id");
		query.setParameter("fullname", fullname);
		query.setParameter("mobileno", mobileno);
		query.setParameter("id", cid);

		query.executeUpdate();
		entityManager.getTransaction().commit();

	}

	@Override
	public int CheckContact(String fullname) {
		EntityManager entityManager = getConnection();
		int count = 0;
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("from ContactDetails where fullname=:name");
		query.setParameter("name", fullname);
		List result = query.getResultList();

		for (Object p : result) {
			count = count + 1;
		}
		// System.out.println("result vaalues" + cou);
		return count;
	}
	
	
	/*
	 * @Override public List getFullNames(String fullname) {
	 * 
	 * EntityManager entityManager = getConnection(); Query query = entityManager.
	 * createQuery("select c.fullName from ContactDetails  c  where c.fullName =:name "
	 * ); query.setParameter("name", fullname); List rows = query.getResultList();
	 * 
	 * return rows; }
	 */
	

}
