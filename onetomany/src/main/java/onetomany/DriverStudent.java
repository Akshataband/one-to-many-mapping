package onetomany;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DriverStudent {

	public static void main(String[] args) {
//		addData();
//		getCollegeById(108);
//		addStudent(108);
//		addCollege();
//		findStudByCollegeId(108);
//		allocateStudent(111,2);
		deallocateStudent(111,2);
//		deleteStudentById(3);
//		updateStudent(2,"rohit",25);
//		deleteCollegeById(108);
	}

	public static void addData() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Student s1 = new Student();
		s1.setName("tappu");
		s1.setAge(22);

		Student s2 = new Student();
		s2.setName("jethalaal");
		s2.setAge(20);

		List<Student> li = new ArrayList();
		li.add(s1);
		li.add(s2);

		College c = new College();
		c.setName("JSPM pvpit");
		c.setLocation("Pune");
		c.setStudent(li);

		et.begin();
		em.persist(c);
		em.persist(s1);
		em.persist(s2);
		et.commit();
	}

//	remove the student from table
	public static void getCollegeById(int c_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		College c = em.find(College.class, c_id);
		System.out.println("College Name : " + c.getName() + " " + c.getLocation());
		et.begin();
		em.merge(c);
		et.commit();
	}

	public static void addStudent(int c_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		College c = em.find(College.class, c_id);

		Student s = new Student();
		s.setName("Allen");
		s.setAge(22);

		List<Student> li = c.getStudent();
		li.add(s);
		c.setStudent(li);

		et.begin();
		em.persist(s);
		em.merge(c);
		et.commit();
	}

	public static void addCollege() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		College c = new College();
		c.setName("MIT");
		c.setLocation("Kothrud");

		et.begin();
		em.persist(c);
		et.commit();
	}

	public static void findStudByCollegeId(int c_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		College c = em.find(College.class, c_id);

		List<Student> li = c.getStudent();
		li.forEach(s -> System.out
				.println("Student ID: " + s.getId() + ", Name: " + s.getName() + ", Age: " + s.getAge()));

	}

	public static void allocateStudent(int c_id, int s_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		et.begin();
		College college = em.find(College.class, c_id);
		Student student = em.find(Student.class, s_id);

		List<Student> students = college.getStudent();
		students.add(student); // add student to college's student list
		college.setStudent(students);

		em.merge(college); // update college

		et.commit();
	}

		public static void deallocateStudent(int c_id ,int s_id){
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
			EntityManager em = emf.createEntityManager();
			EntityTransaction et = em.getTransaction();
			
			et.begin();
		    College college = em.find(College.class, c_id);
		    college.getStudent().removeIf(s -> s.getId() == s_id);
		    em.merge(college);
		    et.commit();
		}

	public static void deleteStudentById(int s_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		et.begin();
		Student student = em.find(Student.class, s_id);
		em.remove(student); // directly remove student
		et.commit();

	}

	public static void updateStudent(int s_id, String newName, int newAge) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Student s = em.find(Student.class, s_id);
		s.setName(newName);
		s.setAge(newAge);

		et.begin();
		em.merge(s);
		et.commit();
	}

	public static void deleteCollegeById(int c_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		College college = em.find(College.class, c_id);

		et.begin();
		em.remove(college);
		et.commit();
	}

}
