package com.example.checkads_backend_admin.Company;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepo {

    @PersistenceContext
    private EntityManager entityManager;

    public Company findById(Integer id) {
        return entityManager.find(Company.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Company> findAll() {
        Query query = entityManager.createQuery("from Company"); // สร้าง Query ดึงข้อมูลทั้งหมดจากตาราง customer
        return query.getResultList(); // ดึงรายการผลลัพธ์จากการ Query ส่งกลับ
    }

    @SuppressWarnings("rawtypes")
    public Company findByUserId(Long userId) {
        Query query = entityManager.createQuery("from Company where userId = :USERID");
        query.setParameter("USERID", userId);
        List resultList = query.getResultList();
        return resultList.isEmpty() ? null : (Company) resultList.get(0);
    }

    @Transactional
    public Company save(Company company) {
        entityManager.persist(company); // insert กรณีไม่มีค่า id ใน object หรือ update กรณีมีค่า id ใน object
        return company;
    }
    
}
