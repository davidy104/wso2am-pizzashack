package org.pizzashack.data.support;

import java.text.DecimalFormat;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.pizzashack.data.Customer;
import org.pizzashack.data.support.EntityBuilder.EntityBuilderManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class InitialDataSetup {
	private TransactionTemplate transactionTemplate;

	@PersistenceContext
	private EntityManager entityManager;

	private Customer customer;

	Random rand = new Random();

	DecimalFormat format = new DecimalFormat("#.##");

	public InitialDataSetup(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void initialize() {
		EntityBuilderManager.setEntityManager(this.entityManager);

		this.transactionTemplate.execute(new TransactionCallback<Void>() {

			@Override
			public Void doInTransaction(TransactionStatus status) {
				// initiate menu
				{
					new MenuItemBuilder() {
						{
							create("Chicken Parmesan",
									"Grilled chicken, fresh tomatoes, feta and mozzarella cheese",
									format.format(rand.nextInt(20) + 10 - 0.01),
									"/images/1.png");
						}
					}.build();

					new MenuItemBuilder() {
						{
							create("Spicy Italian",
									"Pepperoni and a double portion of spicy Italian sausage",
									format.format(rand.nextInt(20) + 10 - 0.01),
									"/images/2.png");
						}
					}.build();

					new MenuItemBuilder() {
						{
							create("Garden Fresh",
									"Slices onions and green peppers, gourmet mushrooms, black olives and ripe Roma tomatoes",
									format.format(rand.nextInt(20) + 10 - 0.01),
									"/images/3.png");
						}
					}.build();

					new MenuItemBuilder() {
						{
							create("Tuscan Six Cheese",
									"Six cheese blend of mozzarella, Parmesan, Romano, Asiago and Fontina",
									format.format(rand.nextInt(20) + 10 - 0.01),
									"/images/4.png");
						}
					}.build();

					new MenuItemBuilder() {
						{
							create("Spinach Alfredo",
									"Rich and creamy blend of spinach and garlic Parmesan with Alfredo saucePepperoni and a double portion of spicy Italian sausage",
									format.format(rand.nextInt(20) + 10 - 0.01),
									"/images/5.png");
						}
					}.build();
					new MenuItemBuilder() {
						{
							create("BBQ Chicken Bacon",
									"Grilled white chicken, hickory-smoked bacon and fresh sliced onions in barbeque sauce",
									format.format(rand.nextInt(20) + 10 - 0.01),
									"/images/6.png");
						}
					}.build();
					new MenuItemBuilder() {
						{
							create("Hawaiian BBQ Chicken",
									"Grilled white chicken, hickory-smoked bacon, barbeque sauce topped with sweet pine-apple",
									format.format(rand.nextInt(20) + 10 - 0.01),
									"/images/7.png");
						}
					}.build();
					new MenuItemBuilder() {
						{
							create("Grilled Chicken Club",
									"Grilled white chicken, hickory-smoked bacon and fresh sliced onions topped with Roma tomatoes",
									format.format(rand.nextInt(20) + 10 - 0.01),
									"/images/8.png");
						}
					}.build();
					new MenuItemBuilder() {
						{
							create("Double Bacon 6Cheese",
									"Hickory-smoked bacon, Julienne cut Canadian bacon, Parmesan, mozzarella, Romano, Asiago and and Fontina cheese",
									format.format(rand.nextInt(20) + 10 - 0.01),
									"/images/9.png");
						}
					}.build();
					new MenuItemBuilder() {
						{
							create("Chilly Chicken Cordon Bleu",
									"Spinash Alfredo sauce topped with grilled chicken, ham, onions and mozzarella",
									format.format(rand.nextInt(20) + 10 - 0.01),
									"/images/10.png");
						}
					}.build();
				}
				// init customer
				{
					InitialDataSetup.this.customer = new CustomerBuilder() {
						{
							create("david", "498492", "12 marvon downs",
									"david.yuan@yellow.co.nz");
						}
					}.build();
				}

				// init orders
				{
					new OrderBuilder() {
						{
							create("Chilly Chicken Cordon Bleu", 1, customer,
									"N");
						}
					}.build();

					new OrderBuilder() {
						{
							create("Double Bacon 6Cheese", 3, customer, "N");
						}
					}.build();

					new OrderBuilder() {
						{
							create("Hawaiian BBQ Chicken", 1, customer, "Y");
						}
					}.build();
				}

				return null;
			}

		});

		EntityBuilderManager.clearEntityManager();

	}
}
