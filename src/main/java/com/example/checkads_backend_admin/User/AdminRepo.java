package com.example.checkads_backend_admin.User;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
public class AdminRepo {
    
    @PersistenceContext
    private EntityManager entityManager;

    public Admin findById(Integer id) {
        return entityManager.find(Admin.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Admin> findAll() {
        Query query = entityManager.createQuery("from Admin"); // สร้าง Query ดึงข้อมูลทั้งหมดจากตาราง customer
        return query.getResultList(); // ดึงรายการผลลัพธ์จากการ Query ส่งกลับ
    }
    
   /* @SuppressWarnings("unchecked")
    public List<Admin> findAdminAll(Long uId) {
        Query query = entityManager.createQuery("from Admin where uId = :UID"); // สร้าง Query ดึงข้อมูลทั้งหมดจากตาราง customer
        query.setParameter("UID", uId);
        return query.getResultList(); // ดึงรายการผลลัพธ์จากการ Query ส่งกลับ
    }*/
    
    @SuppressWarnings("rawtypes")
    public Admin findById(Long id) {
        Query query = entityManager.createQuery("from Admin where id = :ID");
        query.setParameter("ID", id);
        List resultList = query.getResultList();
        return resultList.isEmpty() ? null : (Admin) resultList.get(0);
    }

   

   
    
    @SuppressWarnings("rawtypes")
    public Admin findByEmail(String email) {
        Query query = entityManager.createQuery("from Admin where email = :EMAIL");
        query.setParameter("EMAIL", email);
        List resultList = query.getResultList();
        return resultList.isEmpty() ? null : (Admin) resultList.get(0);
    }
    
  /*  @SuppressWarnings("rawtypes")
    public Admin findByToken(String token) {
        Query query = entityManager.createQuery("from Admin where token = :TOKEN");
        query.setParameter("TOKEN", token);
        List resultList = query.getResultList();
        return resultList.isEmpty() ? null : (Admin) resultList.get(0);
    }*/

    @Transactional
    public Admin save(Admin admin) {
        entityManager.persist(admin); // insert กรณีไม่มีค่า id ใน object หรือ update กรณีมีค่า id ใน object
        return admin;
    }

   /* @Transactional
    public void delete(String cardToken) {
        AdAccount creditCard = entityManager.find(AdAccount.class, cardToken); // ค้นหาตาม id ที่ต้องการลบ
        entityManager.remove(creditCard); // เริ่มลบจริง
    }*/

}
