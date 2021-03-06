
/**
 * This is generated code - to edit code or override methods use - Product class
 * @author witchCraft Code Generator
 * WARNING  - DO NOT EDIT - CHANGES WILL BE OVERWRITTEN
 */

package bizobjects;

import javax.persistence.*;
import org.hibernate.annotations.Cascade;
import org.witchcraft.model.support.audit.Auditable;

import java.util.Date;

@MappedSuperclass
/*@Entity
@Table(name="Product",uniqueConstraints={@UniqueConstraint(columnNames={})})*/
public abstract class ProductBase
		extends
			org.witchcraft.model.support.BusinessEntity
		implements
			java.io.Serializable, Auditable {

	private static final long serialVersionUID = 1L;

	protected String name;

	protected String brand;

	protected double listPrice;

	public String getName() {
		return this.name;
	}

	public String getBrand() {
		return this.brand;
	}

	public double getListPrice() {
		return this.listPrice;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}

	public abstract Product productInstance();

	@Transient
	public String getDisplayName() {
		return name + "";
	}

}
