package com.publicfountain.domain;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import org.witchcraft.model.support.springbeanhelpers.BeanHelper;
import org.witchcraft.model.support.testing.AbstractTestDataFactory;

import org.witchcraft.model.support.testing.TestDataFactory;

import org.witchcraft.model.randomgen.RandomValueGeneratorFactory;

import org.springframework.transaction.annotation.Transactional;

import com.publicfountain.domain.service.CategoryService;

@Transactional
public class CategoryTestDataFactory extends AbstractTestDataFactory<Category> {

	List<Category> categorys = new ArrayList<Category>();

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy.MM.dd HH:mm:ss z");

	CategoryService categoryService;

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void register(Category category) {
		categorys.add(category);
	}

	public Category createCategoryOne() {
		Category category = new Category();

		try {

			category.setName("Wilson");

			TestDataFactory parentTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("categoryTestDataFactory");

			register(category);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return category;
	}

	public Category createCategoryTwo() {
		Category category = new Category();

		try {

			category.setName("pi");

			TestDataFactory parentTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("categoryTestDataFactory");

			register(category);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return category;
	}

	public Category createCategoryThree() {
		Category category = new Category();

		try {

			category.setName("Malissa");

			TestDataFactory parentTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("categoryTestDataFactory");

			register(category);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return category;
	}

	public Category createCategoryFour() {
		Category category = new Category();

		try {

			category.setName("gamma");

			TestDataFactory parentTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("categoryTestDataFactory");

			register(category);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return category;
	}

	public Category createCategoryFive() {
		Category category = new Category();

		try {

			category.setName("John");

			TestDataFactory parentTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("categoryTestDataFactory");

			register(category);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return category;
	}

	public Category loadOneRecord() {
		List<Category> categorys = categoryService.loadAll();

		if (categorys.isEmpty()) {
			persistAll();
			categorys = categoryService.loadAll();
		}

		return categorys.get(new Random().nextInt(categorys.size()));
	}

	public List<Category> getAllAsList() {

		if (categorys.isEmpty()) {
			createCategoryOne();
			createCategoryTwo();
			createCategoryThree();
			createCategoryFour();
			createCategoryFive();

		}

		return categorys;
	}

	public void persistAll() {
		if (!isPersistable() || alreadyPersisted)
			return;

		getAllAsList();

		for (Category category : categorys) {
			categoryService.save(category);
		}

		alreadyPersisted = true;
	}

	/** Execute this method to manually generate additional orders
	 * @param args
	 */
	public static void main(String args[]) {

		int recordsTocreate = 30;

		TestDataFactory placedOrderTestDataFactory = (TestDataFactory) BeanHelper
				.getBean("placedOrderTestDataFactory");

		placedOrderTestDataFactory.createAndSaveRecords(recordsTocreate);
	}

	public void createAndSaveRecords(int recordsTocreate) {
		for (int i = 0; i < recordsTocreate; i++) {
			Category category = createRandomCategory();
			categoryService.save(category);
		}
	}

	public Category createRandomCategory() {
		Category category = new Category();

		category.setName((String) RandomValueGeneratorFactory
				.createInstance("String"));

		TestDataFactory parentTestDataFactory = (TestDataFactory) BeanHelper
				.getBean("categoryTestDataFactory");

		return category;
	}

}
