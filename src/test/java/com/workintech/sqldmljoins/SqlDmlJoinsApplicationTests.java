package com.workintech.sqldmljoins;

import com.workintech.sqldmljoins.entity.StudentNameCount;
import com.workintech.sqldmljoins.repository.KitapRepository;
import com.workintech.sqldmljoins.repository.OgrenciRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(ResultAnalyzer.class)	
class SqlDmlJoinsApplicationTests {

	private KitapRepository kitapRepository;
	private OgrenciRepository ogrenciRepository;

	@Autowired
	public SqlDmlJoinsApplicationTests(KitapRepository kitapRepository, OgrenciRepository ogrenciRepository) {
		this.kitapRepository = kitapRepository;
		this.ogrenciRepository = ogrenciRepository;
	}

	@DisplayName("Dram ve Hikaye türündeki kitapları listeleyin.")
	@Test
	void findBooksTest(){
		assertEquals(kitapRepository.findBooks().size(), 3);
	}

	@DisplayName("Kitap alan öğrencilerin öğrenci bilgilerini listeleyin.")
	@Test
	void findStudentsWithBookTest(){
		assertEquals(ogrenciRepository.findStudentsWithBook().size(), 17);
	}

	@DisplayName("Kitap almayan öğrencileri listeleyin.")
	@Test
	void findStudentsWithNoBookTest(){
		assertEquals(ogrenciRepository.findStudentsWithNoBook().size(), 2);
	}

	@DisplayName("Kitap almayan öğrencileri listeleyin.")
	@Test
	void findClassesWithBookCountTest(){
		assertEquals(ogrenciRepository.findClassesWithBookCount().size(), 2);
		assertEquals(ogrenciRepository.findClassesWithBookCount().get(0).getCount(), 6);
	}

	@DisplayName("Öğrenci tablosundaki öğrenci sayısını gösterin.")
	@Test
	void findStudentCountTest(){
		assertEquals(ogrenciRepository.findStudentCount(), 10);
	}

	@DisplayName("Öğrenci tablosunda kaç farklı isimde öğrenci olduğunu listeleyiniz.")
	@Test
	void findUniqueStudentNameCountTest(){
		assertEquals(ogrenciRepository.findUniqueStudentNameCount(), 9);
	}

	@DisplayName("Öğrenci tablosunda kaç farklı isimde öğrenci olduğunu listeleyiniz.")
	@Test
	void findStudentNameCountTest(){
		List<StudentNameCount> studentNameCountList = ogrenciRepository.findStudentNameCount();
		StudentNameCount sema = studentNameCountList.stream().filter(studentNameCount -> studentNameCount.getAd().equals("Sema"))
						.collect(Collectors.toList()).get(0);

		assertEquals(sema.getCount(), 2);
		assertEquals(ogrenciRepository.findStudentNameCount().size(), 9);
	}

	@DisplayName("Her sınıftaki öğrenci sayısını bulunuz..")
	@Test
	void findStudentClassCountTest(){
		assertEquals(ogrenciRepository.findStudentClassCount().get(0).getSinif(), "9C");
		assertEquals(ogrenciRepository.findStudentClassCount().get(0).getCount(), 2);
		assertEquals(ogrenciRepository.findStudentClassCount().size(), 6);
	}

	@DisplayName("Her öğrencinin ad soyad karşılığında okuduğu kitap sayısını getiriniz.")
	@Test
	void findStudentNameSurnameCountTest(){
		assertEquals(ogrenciRepository.findStudentNameSurnameCount().get(0).getAd(), "Deniz");
		assertEquals(ogrenciRepository.findStudentNameSurnameCount().size(), 8);
	}

	@DisplayName("Tüm kitapların ortalama puanını bulunuz.")
	@Test
	void findAvgPointOfBooksTest(){
		assertEquals(String.format("%.2f", kitapRepository.findAvgPointOfBooks()), "19,42");
	}
}
