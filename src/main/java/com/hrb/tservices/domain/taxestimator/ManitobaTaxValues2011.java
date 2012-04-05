package com.hrb.tservices.domain.taxestimator;

public class ManitobaTaxValues2011 extends ProvincialTaxValues2011 
{
	protected double L6147_FamilyTaxBenefit;
	protected double MB479_FamilyIncome;
	protected double MB479_PersonalTaxCredit;
	
	public ManitobaTaxValues2011(double L5804_Basic, 
								double L5808_Age,
								double L5812_SpouseCLP, 
								double L5816_EligibleDependant,
								double L5824_CPPQPPEmployment, 
								double L5828_CPPQPPSelfEmployment,
								double L5832_EIPremiumsEmployment, 
								double L5836_PensionIncome,
								double L5856_TuitionEducation, 
								double L5868_Medical, 
								double L5876,
								double L5880, 
								double L5884, 
								double L5896_DonationsGifts,
								double L6150_NRTC, 
								double L6152_DividendTaxCredit,
								double Gross_Provincial_Tax, 
								double Net_Provincial_Tax,
								double L6147_FamilyTaxBenefit,  //Manitoba specfic element
								double MB479_FamilyIncome,		//Manitoba specfic element
								double MB479_PersonalTaxCredit) //Manitoba specfic element
	{
		super(	Province.MB,
				L5804_Basic, 
				L5808_Age, 
				L5812_SpouseCLP, 
				L5816_EligibleDependant,
				L5824_CPPQPPEmployment, 
				L5828_CPPQPPSelfEmployment,
				L5832_EIPremiumsEmployment, 
				L5836_PensionIncome,
				L5856_TuitionEducation, 
				L5868_Medical, L5876, 
				L5880, L5884,
				L5896_DonationsGifts, 
				L6150_NRTC, 
				L6152_DividendTaxCredit,
				Gross_Provincial_Tax, 
				Net_Provincial_Tax);
		
				this.L6147_FamilyTaxBenefit = L6147_FamilyTaxBenefit;
				this.MB479_FamilyIncome = MB479_FamilyIncome;
				this.MB479_PersonalTaxCredit = MB479_PersonalTaxCredit;

	}

	public String toString()
	{
		return (new StringBuilder(200)
					.append(province.shortName())
					.append(" [")
					.append(super.toString())
					.append(", L6147_FamilyTaxBenefit: ").append(L6147_FamilyTaxBenefit)
					.append(", MB479_FamilyIncome: ").append(MB479_FamilyIncome)
					.append(", MB479_PersonalTaxCredit: ").append(MB479_PersonalTaxCredit)
					.append("]")).toString();	
	}
}