package JSci.maths;


// Referenced classes of package JSci.maths:
//			AbstractMath, NumericalConstants

public final class SpecialMath extends AbstractMath
	implements NumericalConstants {

	private static final double EPS = 2.2200000000000001E-016D;
	private static final double XMININ = 2.2300000000000001E-308D;
	private static final double ai0cs[] = {
		0.075759944940237967D, 0.0075913808108233399D, 0.00041531313389237001D, 1.0700764634390001E-005D, -7.90117997921E-006D, -7.8261435014000004E-007D, 2.7838499428999999E-007D, 8.2524726000000001E-009D, -1.204463945E-008D, 1.55964859E-009D, 
		2.2925562999999999E-010D, -1.1916228E-010D, 1.757854E-011D, 1.12822E-012D, -1.14684E-012D, 2.7154999999999999E-013D, -2.415E-014D, -6.0800000000000002E-015D, 3.1400000000000001E-015D, -7.1E-016D, 
		7.0000000000000003E-017D
	};
	private static final double ai02cs[] = {
		0.054490411014108821D, 0.0033691164782556899D, 6.8897583469179995E-005D, 2.8913705208200001E-006D, 2.0489185893E-007D, 2.2666689910000001E-008D, 3.3962320300000001E-009D, 4.9406022E-010D, 1.188914E-011D, -3.1499150000000002E-011D, 
		-1.3215799999999999E-011D, -1.7941900000000001E-012D, 7.1800999999999999E-013D, 3.8529000000000002E-013D, 1.5390000000000001E-014D, -4.1509999999999998E-014D, -9.5399999999999996E-015D, 3.8199999999999998E-015D, 1.76E-015D, -3.4E-016D, 
		-2.7E-016D, 3.0000000000000001E-017D
	};
	private static final double ai1cs[] = {
		-0.02846744181881479D, -0.01922953231443221D, -0.00061151858579436996D, -2.0699712533499999E-005D, 8.5856191458099997E-006D, 1.04949824671E-006D, -2.9183389184E-007D, -1.5593781459999999E-008D, 1.318012367E-008D, -1.4484234099999999E-009D, 
		-2.9085121999999999E-010D, 1.2663889000000001E-010D, -1.664947E-011D, -1.66665E-012D, 1.2426E-012D, -2.7315E-013D, 2.0229999999999999E-014D, 7.2999999999999995E-015D, -3.3300000000000001E-015D, 7.1E-016D, 
		-6.0000000000000001E-017D
	};
	private static final double ai12cs[] = {
		0.02857623501828014D, -0.0097610974913614704D, -0.00011058893876263D, -3.8825648088700004E-006D, -2.5122362377000002E-007D, -2.6314688470000001E-008D, -3.8353803899999999E-009D, -5.5897433000000004E-010D, -1.8974950000000001E-011D, 3.252602E-011D, 
		1.4125799999999999E-011D, 2.03564E-012D, -7.1985000000000001E-013D, -4.0835999999999999E-013D, -2.101E-014D, 4.2729999999999998E-014D, 1.041E-014D, -3.8199999999999998E-015D, -1.8599999999999999E-015D, 3.2999999999999999E-016D, 
		2.8000000000000001E-016D, -3.0000000000000001E-017D
	};
	private static final double aifcs[] = {
		-0.03797135849667D, 0.059191888537263641D, 0.00098629280577279977D, 6.8488438190765601E-006D, 2.5942025962190001E-008D, 6.1766127740000006E-011D, 1.0092454E-013D, 1.2013999999999999E-016D, 9.9999999999999998E-020D
	};
	private static final double aigcs[] = {
		0.018152365581161269D, 0.02157256316601076D, 0.00025678356987483001D, 1.42652141197E-006D, 4.5721149200000002E-009D, 9.5251700000000003E-012D, 1.3920000000000001E-014D, 1.0000000000000001E-017D
	};
	private static final double aipcs[] = {
		-0.018751929779386799D, -0.0091443848250054999D, 0.00090104573378249999D, -0.0001394184127221D, 2.73815815785E-005D, -6.2750421118999996E-006D, 1.6064844184000001E-006D, -4.4763921579999998E-007D, 1.334635874E-007D, -4.2073533399999997E-008D, 
		1.3902199E-008D, -4.7831847999999999E-009D, 1.7047897E-009D, -6.2683890000000004E-010D, 2.3698239999999998E-010D, -9.1864099999999995E-011D, 3.64278E-011D, -1.4747499999999999E-011D, 6.0851000000000004E-012D, -2.5551999999999999E-012D, 
		1.0906E-012D, -4.7249999999999996E-013D, 2.0759999999999999E-013D, -9.2400000000000003E-014D, 4.1700000000000002E-014D, -1.9000000000000001E-014D, 8.7000000000000002E-015D, -4.0000000000000003E-015D, 1.9000000000000001E-015D, -9.0000000000000003E-016D, 
		3.9999999999999999E-016D, -2E-016D, 9.9999999999999998E-017D, -0D
	};
	private static final double am21cs[] = {
		0.0065809191761484996D, 0.0023675984685722D, 0.00013247416703709999D, 1.5760090404300001E-005D, 2.7529702663000001E-006D, 6.1026790170000003E-007D, 1.5950884680000001E-007D, 4.71033947E-008D, 1.5293387099999999E-008D, 5.3590722000000001E-009D, 
		2.0000910000000002E-009D, 7.8722920000000003E-010D, 3.2431029999999998E-010D, 1.390106E-010D, 6.1701100000000006E-011D, 2.82491E-011D, 1.32979E-011D, 6.4188000000000001E-012D, 3.1697000000000001E-012D, 1.5981E-012D, 
		8.2130000000000004E-013D, 4.2960000000000001E-013D, 2.2840000000000002E-013D, 1.232E-013D, 6.7500000000000003E-014D, 3.7399999999999997E-014D, 2.0999999999999999E-014D, 1.19E-014D, 6.8000000000000001E-015D, 3.9000000000000003E-015D, 
		2.2999999999999999E-015D, 1.3E-015D, 7.9999999999999998E-016D, 5.0000000000000004E-016D, 2.9999999999999999E-016D, 9.9999999999999998E-017D, 9.9999999999999998E-017D, 0, 0, 0
	};
	private static final double ath1cs[] = {
		-0.071258378156693655D, -0.0059047197983145101D, -0.00012114544069499D, -9.8860854227000007E-006D, -1.3808409735199999E-006D, -2.6142640171999998E-007D, -6.0504325890000002E-008D, -1.6184362229999999E-008D, -4.8346491099999996E-009D, -1.57655272E-009D, 
		-5.5231518000000005E-010D, -2.0545441E-010D, -8.0434120000000001E-011D, -3.291252E-011D, -1.3998750000000001E-011D, -6.1615099999999997E-012D, -2.7961399999999998E-012D, -1.30428E-012D, -6.2373000000000001E-013D, -3.0512000000000002E-013D, 
		-1.5238999999999999E-013D, -7.7579999999999996E-014D, -4.0200000000000002E-014D, -2.117E-014D, -1.1319999999999999E-014D, -6.1400000000000004E-015D, -3.3699999999999999E-015D, -1.8800000000000002E-015D, -1.0499999999999999E-015D, -5.9999999999999999E-016D, 
		-3.4E-016D, -2E-016D, -1.1E-016D, -7.0000000000000003E-017D, -4.0000000000000003E-017D, -2.0000000000000001E-017D
	};
	private static final double am22cs[] = {
		-0.01562844480625341D, 0.0077833644523968102D, 0.00086705777047717996D, 0.00015696627315611001D, 3.563962571432E-005D, 9.2459833542499997E-006D, 2.6211016185000001E-006D, 7.9188221650999998E-007D, 2.5104152792E-007D, 8.2652232060000001E-008D, 
		2.8057116620000001E-008D, 9.7682108999999996E-009D, 3.4740792300000001E-009D, 1.25828132E-009D, 4.6298826000000003E-010D, 1.7272824999999999E-010D, 6.5231920000000001E-011D, 2.4904710000000001E-011D, 9.6015600000000001E-012D, 3.7344800000000001E-012D, 
		1.46417E-012D, 5.7826E-013D, 2.2990999999999998E-013D, 9.1969999999999998E-014D, 3.7E-014D, 1.496E-014D, 6.0800000000000002E-015D, 2.4800000000000001E-015D, 1.01E-015D, 4.1000000000000001E-016D, 
		1.7E-016D, 7.0000000000000003E-017D, 2.0000000000000001E-017D
	};
	private static final double ath2cs[] = {
		0.0044052734587187699D, -0.030429194523184551D, -0.0013856532837717901D, -0.00018044439089549D, -3.380847108327E-005D, -7.6781835352199996E-006D, -1.96783944371E-006D, -5.4837271158000001E-007D, -1.6254615505E-007D, -5.0530499809999999E-008D, 
		-1.6315807010000001E-008D, -5.4342041099999996E-009D, -1.85739855E-009D, -6.4895119999999999E-010D, -2.3105948000000001E-010D, -8.3632819999999999E-011D, -3.0711959999999997E-011D, -1.142367E-011D, -4.2981100000000002E-012D, -1.63389E-012D, 
		-6.2693000000000004E-013D, -2.426E-013D, -9.461E-014D, -3.7160000000000003E-014D, -1.4689999999999998E-014D, -5.8400000000000002E-015D, -2.33E-015D, -9.2999999999999993E-016D, -3.7E-016D, -1.5E-016D, 
		-6.0000000000000001E-017D, -2.0000000000000001E-017D
	};
	private static final double bi0cs[] = {
		-0.076605472528391449D, 1.9273379539938083D, 0.22826445869203013D, 0.013048914667072904D, 0.00043442709008164872D, 9.4226576860019292E-006D, 1.4340062895106E-007D, 1.61384906966E-009D, 1.3966500439999999E-011D, 9.5794509999999997E-014D, 
		5.3338999999999998E-016D, 2.4500000000000001E-018D
	};
	private static final double bj0cs[] = {
		0.10025416196893913D, -0.66522300776440513D, 0.24898370349828131D, -0.033252723170035768D, 0.0023114179304694017D, -9.9112774199508006E-005D, 2.8916708643997999E-006D, -6.1210858662999997E-008D, 9.838650793000001E-010D, -1.2423551500000001E-011D, 
		1.265433E-013D, -1.0619E-015D, 7.4000000000000007E-018D
	};
	private static final double bm0cs[] = {
		0.092849616373816446D, -0.0014298770740348401D, 2.8305792712570001E-005D, -1.43300611424E-006D, 1.2028628045999999E-007D, -1.3971130129999999E-008D, 2.04076188E-009D, -3.5399669000000001E-010D, 7.0247589999999995E-011D, -1.5541069999999999E-011D, 
		3.7622600000000002E-012D, -9.8282000000000005E-013D, 2.7407999999999998E-013D, -8.0910000000000006E-014D, 2.511E-014D, -8.1400000000000005E-015D, 2.7500000000000001E-015D, -9.6000000000000002E-016D, 3.4E-016D, -1.2E-016D, 
		4.0000000000000003E-017D
	};
	private static final double bth0cs[] = {
		-0.24639163774300119D, 0.001737098307508963D, -6.2183633402967999E-005D, 4.3680501657420001E-006D, -4.56093019869E-007D, 6.2197400101000003E-008D, -1.0300442889E-008D, 1.979526776E-009D, -4.2819839600000001E-010D, 1.0203584000000001E-010D, 
		-2.6363898000000001E-011D, 7.2979350000000002E-012D, -2.144188E-012D, 6.6369299999999999E-013D, -2.15126E-013D, 7.2659000000000006E-014D, -2.5464999999999999E-014D, 9.2290000000000007E-015D, -3.448E-015D, 1.325E-015D, 
		-5.22E-016D, 2.1000000000000001E-016D, -8.6999999999999996E-017D, 3.5999999999999999E-017D
	};
	private static final double by0cs[] = {
		-0.011277839392865573D, -0.12834523756042035D, -0.10437884799794249D, 0.023662749183969694D, -0.0020903916477004862D, 0.000103975453939057D, -3.3697471624230001E-006D, 7.7293842676000001E-008D, -1.3249767720000001E-009D, 1.7648231999999999E-011D, 
		-1.8810500000000001E-013D, 1.641E-015D, -1.1E-017D
	};
	private static final double bi1cs[] = {
		-0.0019717132610998591D, 0.40734887667546482D, 0.034838994299959458D, 0.001545394556300123D, 4.1888521098377003E-005D, 7.6490267648300004E-007D, 1.0042493924E-008D, 9.9322077000000003E-011D, 7.6638000000000001E-013D, 4.7410000000000003E-015D, 
		2.3999999999999999E-017D
	};
	private static final double bj1cs[] = {
		-0.11726141513332787D, -0.2536152183079064D, 0.050127080984469566D, -0.0046315148096250812D, 0.000247996229415914D, -8.6789486862780003E-006D, 2.1429391714300001E-007D, -3.9360930789999997E-009D, 5.5911823000000003E-011D, -6.3276099999999999E-013D, 
		5.8400000000000002E-015D, -4.4E-017D
	};
	private static final double bm1cs[] = {
		0.10473625109312849D, 0.0044244389370234503D, -5.6616395040350002E-005D, 2.3134941733900001E-006D, -1.7377182007000001E-007D, 1.8932099300000001E-008D, -2.6541602299999998E-009D, 4.4740208999999998E-010D, -8.6917950000000001E-011D, 1.8914919999999999E-011D, 
		-4.5188400000000002E-012D, 1.16765E-012D, -3.2264999999999999E-013D, 9.4500000000000001E-014D, -2.9129999999999998E-014D, 9.3899999999999999E-015D, -3.1499999999999999E-015D, 1.09E-015D, -3.8999999999999998E-016D, 1.4000000000000001E-016D, 
		-4.9999999999999999E-017D
	};
	private static final double bth1cs[] = {
		0.74060141026313853D, -0.0045717556596376902D, 0.000119818510964326D, -6.9645618916479998E-006D, 6.5549562144700005E-007D, -8.4066228944999997E-008D, 1.3376886563999999E-008D, -2.4995656539999999E-009D, 5.2949509999999997E-010D, -1.2413594400000001E-010D, 
		3.1656484999999997E-011D, -8.6686399999999993E-012D, 2.5237580000000002E-012D, -7.7508500000000002E-013D, 2.4952699999999998E-013D, -8.3772999999999996E-014D, 2.9205E-014D, -1.0534E-014D, 3.919E-015D, -1.4999999999999999E-015D, 
		5.8900000000000001E-016D, -2.3700000000000001E-016D, 9.6999999999999998E-017D, -4.0000000000000003E-017D
	};
	private static final double by1cs[] = {
		0.032080471006119084D, 1.2627078974335004D, 0.0064999618999231753D, -0.089361645288605046D, 0.013250881221757096D, -0.00089790591196483519D, 3.6473614879583062E-005D, -1.001374381666E-006D, 1.9945396573900002E-008D, -3.0230656018000003E-010D, 
		3.6098781500000001E-012D, -3.4874879999999999E-014D, 2.7838E-016D, -1.8600000000000001E-018D
	};
	private static final double LOGSQRT2PI = Math.log(Math.sqrt(6.2831853071795862D));
	private static final double logGamma_xBig = 2.5499999999999999E+305D;
	private static double logGammaCache_res;
	private static double logGammaCache_x;
	private static final int MAX_ITERATIONS = 150;
	private static final double PRECISION = 8.8800000000000003E-016D;
	private static double logBetaCache_res;
	private static double logBetaCache_p;
	private static double logBetaCache_q;

	private SpecialMath() {
	}

	public static double chebyshev(double d, double ad[]) {
		double d2 = 0.0D;
		double d3 = 0.0D;
		double d4 = 0.0D;
		double d1 = 2D * d;
		for (int i = ad.length - 1; i > -1; i--) {
			d4 = d3;
			d3 = d2;
			d2 = (d1 * d3 - d4) + ad[i];
		}

		return 0.5D * (d2 - d4);
	}

	public static double airy(double d) {
		if (d < -1D) {
			double ad[] = airyModPhase(d);
			return ad[0] * Math.cos(ad[1]);
		}
		if (d > 1.0D) {
			return expAiry(d) * Math.exp((-2D * d * Math.sqrt(d)) / 3D);
		} else {
			double d1 = d * d * d;
			return 0.375D + (chebyshev(d1, aifcs) - d * (0.25D + chebyshev(d1, aigcs)));
		}
	}

	private static double[] airyModPhase(double d) {
		double ad[] = new double[2];
		if (d < -2D) {
			double d1 = 16D / (d * d * d) + 1.0D;
			ad[0] = 0.3125D + chebyshev(d1, am21cs);
			ad[1] = -0.625D + chebyshev(d1, ath1cs);
		} else {
			double d2 = (16D / (d * d * d) + 9D) / 7D;
			ad[0] = 0.3125D + chebyshev(d2, am22cs);
			ad[1] = -0.625D + chebyshev(d2, ath2cs);
		}
		double d3 = Math.sqrt(-d);
		ad[0] = Math.sqrt(ad[0] / d3);
		ad[1] = 0.78539816339744828D - d * d3 * ad[1];
		return ad;
	}

	private static double expAiry(double d) {
		if (d < -1D) {
			double ad[] = airyModPhase(d);
			return ad[0] * Math.cos(ad[1]);
		}
		if (d <= 1.0D) {
			double d1 = d * d * d;
			return 0.375D + (chebyshev(d1, aifcs) - d * (0.25D + chebyshev(d1, aigcs))) * Math.exp((2D * d * Math.sqrt(d)) / 3D);
		} else {
			double d2 = Math.sqrt(d);
			double d3 = 2D / (d * d2) - 1.0D;
			return (0.28125D + chebyshev(d3, aipcs)) / Math.sqrt(d2);
		}
	}

	public static double besselFirstZero(double d) {
		double d1 = Math.abs(d);
		if (d1 > 4D) {
			double d2 = 32D / (d1 * d1) - 1.0D;
			double d3 = (0.75D + chebyshev(d2, bm0cs)) / Math.sqrt(d1);
			double d4 = (d1 - 0.78539816339744828D) + chebyshev(d2, bth0cs) / d1;
			return d3 * Math.cos(d4);
		}
		if (d1 == 0.0D)
			return 1.0D;
		else
			return chebyshev(0.125D * d1 * d1 - 1.0D, bj0cs);
	}

	public static double modBesselFirstZero(double d) {
		double d1 = Math.abs(d);
		if (d1 > 3D)
			return Math.exp(d1) * expModBesselFirstZero(d);
		else
			return 2.75D + chebyshev((d1 * d1) / 4.5D - 1.0D, bi0cs);
	}

	private static double expModBesselFirstZero(double d) {
		double d1 = Math.abs(d);
		if (d1 > 3D) {
			if (d1 > 8D)
				return (0.375D + chebyshev(16D / d1 - 1.0D, ai02cs)) / Math.sqrt(d1);
			else
				return (0.375D + chebyshev((48D / d1 - 11D) / 5D, ai0cs)) / Math.sqrt(d1);
		} else {
			return Math.exp(-d1) * (2.75D + chebyshev((d1 * d1) / 4.5D - 1.0D, bi0cs));
		}
	}

	public static double besselFirstOne(double d) {
		double d1 = Math.abs(d);
		if (d1 > 4D) {
			double d2 = 32D / (d1 * d1) - 1.0D;
			double d3 = (0.75D + chebyshev(d2, bm1cs)) / Math.sqrt(d1);
			double d4 = (d1 - 2.3561944901923448D) + chebyshev(d2, bth1cs) / d1;
			return (Math.abs(d3) * d * Math.cos(d4)) / Math.abs(d);
		}
		if (d1 == 0.0D)
			return 0.0D;
		else
			return d * (0.25D + chebyshev(0.125D * d1 * d1 - 1.0D, bj1cs));
	}

	public static double modBesselFirstOne(double d) {
		double d1 = Math.abs(d);
		if (d1 > 3D)
			return Math.exp(d1) * expModBesselFirstOne(d);
		if (d1 == 0.0D)
			return 0.0D;
		else
			return d * (0.875D + chebyshev((d1 * d1) / 4.5D - 1.0D, bi1cs));
	}

	private static double expModBesselFirstOne(double d) {
		double d1 = Math.abs(d);
		if (d1 > 3D)
			if (d1 > 8D)
				return ((d / d1) * (0.375D + chebyshev(16D / d1 - 1.0D, ai12cs))) / Math.sqrt(d1);
			else
				return ((d / d1) * (0.375D + chebyshev((48D / d1 - 11D) / 5D, ai1cs))) / Math.sqrt(d1);
		if (d1 == 0.0D)
			return 0.0D;
		else
			return Math.exp(-d1) * d * (0.875D + chebyshev((d1 * d1) / 4.5D - 1.0D, bi1cs));
	}

	public static double besselSecondZero(double d) {
		if (d > 4D) {
			double d1 = 32D / (d * d) - 1.0D;
			double d2 = (0.75D + chebyshev(d1, bm0cs)) / Math.sqrt(d);
			double d3 = (d - 0.78539816339744828D) + chebyshev(d1, bth0cs) / d;
			return d2 * Math.sin(d3);
		} else {
			return (Math.log(0.5D) + Math.log(d)) * besselFirstZero(d) + 0.375D + (chebyshev(0.125D * d * d - 1.0D, by0cs) * 2D) / 3.1415926535897931D;
		}
	}

	public static double besselSecondOne(double d) {
		if (d > 4D) {
			double d1 = 32D / (d * d) - 1.0D;
			double d2 = (0.75D + chebyshev(d1, bm1cs)) / Math.sqrt(d);
			double d3 = (d - 2.3561944901923448D) + chebyshev(d1, bth1cs) / d;
			return d2 * Math.sin(d3);
		} else {
			return (2D * Math.log(0.5D * d) * besselFirstOne(d)) / 3.1415926535897931D + (0.5D + chebyshev(0.125D * d * d - 1.0D, by1cs)) / d;
		}
	}

	public static double gamma(double d) {
		double ad[] = {
			-1.716185138865495D, 24.76565080557592D, -379.80425647094563D, 629.3311553128184D, 866.96620279041326D, -31451.272968848367D, -36144.413418691176D, 66456.143820240541D
		};
		double ad1[] = {
			-30.840230011973897D, 315.35062697960416D, -1015.1563674902192D, -3107.7716715723109D, 22538.11842098015D, 4755.8462775278813D, -134659.95986496931D, -115132.25967555349D
		};
		double ad2[] = {
			-0.0019104440777279999D, 0.00084171387781295005D, -0.00059523799130430121D, 0.0007936507935003503D, -0.0027777777777776816D, 0.083333333333333329D, 0.0057083835261000004D
		};
		double d1 = 171.624D;
		double d2 = 1.0D;
		int l = 0;
		double d5 = d;
		boolean flag = false;
		if (d5 <= 0.0D) {
			d5 = -d;
			double d7 = (int)d5;
			double d9 = d5 - d7;
			if (d9 != 0.0D) {
				if (d7 != (double)(int)(d7 * 0.5D) * 2D)
					flag = true;
				d2 = -3.1415926535897931D / Math.sin(3.1415926535897931D * d9);
				d5++;
			} else {
				return 1.7976931348623157E+308D;
			}
		}
		double d10;
		if (d5 < 2.2200000000000001E-016D) {
			if (d5 >= 2.2300000000000001E-308D)
				d10 = 1.0D / d5;
			else
				return 1.7976931348623157E+308D;
		} else
		if (d5 < 12D) {
			double d8 = d5;
			double d6;
			if (d5 < 1.0D) {
				d6 = d5;
				d5++;
			} else {
				l = (int)d5 - 1;
				d5 -= l;
				d6 = d5 - 1.0D;
			}
			double d4 = 0.0D;
			double d3 = 1.0D;
			for (int i = 0; i < 8; i++) {
				d4 = (d4 + ad[i]) * d6;
				d3 = d3 * d6 + ad1[i];
			}

			d10 = d4 / d3 + 1.0D;
			if (d8 < d5)
				d10 /= d8;
			else
			if (d8 > d5) {
				for (int j = 0; j < l; j++) {
					d10 *= d5;
					d5++;
				}

			}
		} else
		if (d5 <= d1) {
			double d12 = d5 * d5;
			double d11 = ad2[6];
			for (int k = 0; k < 6; k++)
				d11 = d11 / d12 + ad2[k];

			d11 = (d11 / d5 - d5) + LOGSQRT2PI;
			d11 += (d5 - 0.5D) * Math.log(d5);
			d10 = Math.exp(d11);
		} else {
			return 1.7976931348623157E+308D;
		}
		if (flag)
			d10 = -d10;
		if (d2 != 1.0D)
			d10 = d2 / d10;
		return d10;
	}

	public static double logGamma(double d) {
		double d1 = -0.57721566490153287D;
		double d2 = 0.42278433509846713D;
		double d3 = 1.791759469228055D;
		double ad[] = {
			4.9452353592967269D, 201.8112620856775D, 2290.8383738313464D, 11319.672059033808D, 28557.246356716354D, 38484.962284437934D, 26377.487876241954D, 7225.8139797002877D
		};
		double ad1[] = {
			67.482125503037778D, 1113.3323938571993D, 7738.7570569353984D, 27639.870744033407D, 54993.102062261576D, 61611.221800660023D, 36351.275915019403D, 8785.5363024310136D
		};
		double ad2[] = {
			4.974607845568932D, 542.4138599891071D, 15506.938649783649D, 184793.29044456323D, 1088204.7694688288D, 3338152.9679870298D, 5106661.6789273527D, 3074109.0548505397D
		};
		double ad3[] = {
			183.03283993705926D, 7765.0493214450062D, 133190.38279660742D, 1136705.8213219696D, 5267964.1174379466D, 13467014.543111017D, 17827365.303532742D, 9533095.5918443538D
		};
		double ad4[] = {
			14745.021660599399D, 2426813.3694867045D, 121475557.40450932D, 2663432449.6309772D, 29403789566.345539D, 170266573776.53989D, 492612579337.7431D, 560625185622.39514D
		};
		double ad5[] = {
			2690.5301758708993D, 639388.56543000927D, 41355999.302413881D, 1120872109.616148D, 14886137286.788137D, 101680358627.24382D, 341747634550.73773D, 446315818741.97131D
		};
		double ad6[] = {
			-0.0019104440777279999D, 0.00084171387781295005D, -0.00059523799130430121D, 0.0007936507935003503D, -0.0027777777777776816D, 0.083333333333333329D, 0.0057083835261000004D
		};
		double d4 = 2.2499999999999999E+076D;
		double d5 = 0.6796875D;
		if (d == logGammaCache_x)
			return logGammaCache_res;
		double d16 = d;
		double d21;
		if (d16 > 0.0D && d16 <= 2.5499999999999999E+305D) {
			if (d16 <= 2.2200000000000001E-016D)
				d21 = -Math.log(d16);
			else
			if (d16 <= 1.5D) {
				double d10;
				double d17;
				if (d16 < d5) {
					d10 = -Math.log(d16);
					d17 = d16;
				} else {
					d10 = 0.0D;
					d17 = d16 - 1.0D;
				}
				if (d16 <= 0.5D || d16 >= d5) {
					double d6 = 1.0D;
					double d12 = 0.0D;
					for (int i = 0; i < 8; i++) {
						d12 = d12 * d17 + ad[i];
						d6 = d6 * d17 + ad1[i];
					}

					d21 = d10 + d17 * (d1 + d17 * (d12 / d6));
				} else {
					double d18 = d16 - 1.0D;
					double d7 = 1.0D;
					double d13 = 0.0D;
					for (int j = 0; j < 8; j++) {
						d13 = d13 * d18 + ad2[j];
						d7 = d7 * d18 + ad3[j];
					}

					d21 = d10 + d18 * (d2 + d18 * (d13 / d7));
				}
			} else
			if (d16 <= 4D) {
				double d19 = d16 - 2D;
				double d8 = 1.0D;
				double d14 = 0.0D;
				for (int k = 0; k < 8; k++) {
					d14 = d14 * d19 + ad2[k];
					d8 = d8 * d19 + ad3[k];
				}

				d21 = d19 * (d2 + d19 * (d14 / d8));
			} else
			if (d16 <= 12D) {
				double d20 = d16 - 4D;
				double d9 = -1D;
				double d15 = 0.0D;
				for (int l = 0; l < 8; l++) {
					d15 = d15 * d20 + ad4[l];
					d9 = d9 * d20 + ad5[l];
				}

				d21 = d3 + d20 * (d15 / d9);
			} else {
				d21 = 0.0D;
				if (d16 <= d4) {
					d21 = ad6[6];
					double d22 = d16 * d16;
					for (int i1 = 0; i1 < 6; i1++)
						d21 = d21 / d22 + ad6[i1];

				}
				d21 /= d16;
				double d11 = Math.log(d16);
				d21 = (d21 + LOGSQRT2PI) - 0.5D * d11;
				d21 += d16 * (d11 - 1.0D);
			}
		} else {
			d21 = 1.7976931348623157E+308D;
		}
		logGammaCache_x = d;
		logGammaCache_res = d21;
		return d21;
	}

	public static double incompleteGamma(double d, double d1) {
		if (d1 <= 0.0D || d <= 0.0D || d > 2.5499999999999999E+305D)
			return 0.0D;
		if (d1 < d + 1.0D)
			return gammaSeriesExpansion(d, d1);
		else
			return 1.0D - gammaFraction(d, d1);
	}

	private static double gammaSeriesExpansion(double d, double d1) {
		double d2 = d;
		double d3 = 1.0D / d;
		double d4 = d3;
		for (int i = 1; i < 150; i++) {
			d2++;
			d3 *= d1 / d2;
			d4 += d3;
			if (d3 < d4 * 8.8800000000000003E-016D)
				return d4 * Math.exp((-d1 + d * Math.log(d1)) - logGamma(d));
		}

		return 0.0D;
	}

	private static double gammaFraction(double d, double d1) {
		double d2 = (d1 + 1.0D) - d;
		double d3 = 4.4843049327354256E+307D;
		double d4 = 1.0D / d2;
		double d5 = d4;
		double d6 = 0.0D;
		for (int i = 1; i < 150 && Math.abs(d6 - 1.0D) > 8.8800000000000003E-016D; i++) {
			double d7 = (double)(-i) * ((double)i - d);
			d2 += 2D;
			d4 = d7 * d4 + d2;
			d3 = d2 + d7 / d3;
			if (Math.abs(d3) < 2.2300000000000001E-308D)
				d3 = 2.2300000000000001E-308D;
			if (Math.abs(d4) < 2.2300000000000001E-308D)
				d3 = 2.2300000000000001E-308D;
			d4 = 1.0D / d4;
			d6 = d4 * d3;
			d5 *= d6;
		}

		return Math.exp((-d1 + d * Math.log(d1)) - logGamma(d)) * d5;
	}

	public static double beta(double d, double d1) {
		if (d <= 0.0D || d1 <= 0.0D || d + d1 > 2.5499999999999999E+305D)
			return 0.0D;
		else
			return Math.exp(logBeta(d, d1));
	}

	public static double logBeta(double d, double d1) {
		if (d != logBetaCache_p || d1 != logBetaCache_q) {
			logBetaCache_p = d;
			logBetaCache_q = d1;
			if (d <= 0.0D || d1 <= 0.0D || d + d1 > 2.5499999999999999E+305D)
				logBetaCache_res = 0.0D;
			else
				logBetaCache_res = (logGamma(d) + logGamma(d1)) - logGamma(d + d1);
		}
		return logBetaCache_res;
	}

	public static double incompleteBeta(double d, double d1, double d2) {
		if (d <= 0.0D)
			return 0.0D;
		if (d >= 1.0D)
			return 1.0D;
		if (d1 <= 0.0D || d2 <= 0.0D || d1 + d2 > 2.5499999999999999E+305D)
			return 0.0D;
		double d3 = Math.exp(-logBeta(d1, d2) + d1 * Math.log(d) + d2 * Math.log(1.0D - d));
		if (d < (d1 + 1.0D) / (d1 + d2 + 2D))
			return (d3 * betaFraction(d, d1, d2)) / d1;
		else
			return 1.0D - (d3 * betaFraction(1.0D - d, d2, d1)) / d2;
	}

	private static double betaFraction(double d, double d1, double d2) {
		double d6 = 1.0D;
		double d3 = d1 + d2;
		double d4 = d1 + 1.0D;
		double d5 = d1 - 1.0D;
		double d9 = 1.0D - (d3 * d) / d4;
		if (Math.abs(d9) < 2.2300000000000001E-308D)
			d9 = 2.2300000000000001E-308D;
		d9 = 1.0D / d9;
		double d10 = d9;
		int i = 1;
		for (double d8 = 0.0D; i <= 150 && Math.abs(d8 - 1.0D) > 8.8800000000000003E-016D; i++) {
			int j = 2 * i;
			double d7 = ((double)i * (d2 - (double)i) * d) / ((d5 + (double)j) * (d1 + (double)j));
			d9 = 1.0D + d7 * d9;
			if (Math.abs(d9) < 2.2300000000000001E-308D)
				d9 = 2.2300000000000001E-308D;
			d9 = 1.0D / d9;
			d6 = 1.0D + d7 / d6;
			if (Math.abs(d6) < 2.2300000000000001E-308D)
				d6 = 2.2300000000000001E-308D;
			d10 *= d9 * d6;
			d7 = (-(d1 + (double)i) * (d3 + (double)i) * d) / ((d1 + (double)j) * (d4 + (double)j));
			d9 = 1.0D + d7 * d9;
			if (Math.abs(d9) < 2.2300000000000001E-308D)
				d9 = 2.2300000000000001E-308D;
			d9 = 1.0D / d9;
			d6 = 1.0D + d7 / d6;
			if (Math.abs(d6) < 2.2300000000000001E-308D)
				d6 = 2.2300000000000001E-308D;
			d8 = d9 * d6;
			d10 *= d8;
		}

		return d10;
	}

	public static double error(double d) {
		double d1 = 0.12837916709551259D;
		double ad[] = {
			0.12837916709551256D, -0.3250421072470015D, -0.02848174957559851D, -0.0057702702964894416D, -2.3763016656650163E-005D
		};
		double ad1[] = {
			0.39791722395915535D, 0.065022249988767294D, 0.0050813062818757656D, 0.00013249473800432164D, -3.9602282787753681E-006D
		};
		double ad2[] = {
			-0.0023621185607526594D, 0.41485611868374833D, -0.37220787603570132D, 0.31834661990116175D, -0.11089469428239668D, 0.035478304325618236D, -0.0021663755948687908D
		};
		double ad3[] = {
			0.10642088040084423D, 0.54039791770217105D, 0.071828654414196266D, 0.12617121980876164D, 0.013637083912029051D, 0.011984499846799107D
		};
		double d2 = 0.84506291151046753D;
		double d10 = d < 0.0D ? -d : d;
		double d9;
		if (d10 < 0.84375D) {
			if (d10 < 3.7252902984619141E-009D) {
				d9 = d10 + d10 * d1;
			} else {
				double d7 = d * d;
				double d3 = ad[0] + d7 * (ad[1] + d7 * (ad[2] + d7 * (ad[3] + d7 * ad[4])));
				double d5 = 1.0D + d7 * (ad1[0] + d7 * (ad1[1] + d7 * (ad1[2] + d7 * (ad1[3] + d7 * ad1[4]))));
				d9 = d10 + d10 * (d3 / d5);
			}
		} else
		if (d10 < 1.25D) {
			double d8 = d10 - 1.0D;
			double d4 = ad2[0] + d8 * (ad2[1] + d8 * (ad2[2] + d8 * (ad2[3] + d8 * (ad2[4] + d8 * (ad2[5] + d8 * ad2[6])))));
			double d6 = 1.0D + d8 * (ad3[0] + d8 * (ad3[1] + d8 * (ad3[2] + d8 * (ad3[3] + d8 * (ad3[4] + d8 * ad3[5])))));
			d9 = d2 + d4 / d6;
		} else
		if (d10 >= 6D)
			d9 = 1.0D;
		else
			d9 = 1.0D - complementaryError(d10);
		if (d >= 0.0D)
			return d9;
		else
			return -d9;
	}

	public static double complementaryError(double d) {
		double ad[] = {
			-0.0098649440348471482D, -0.69385857270718176D, -10.558626225323291D, -62.375332450326006D, -162.39666946257347D, -184.60509290671104D, -81.287435506306593D, -9.8143293441691455D
		};
		double ad1[] = {
			19.651271667439257D, 137.65775414351904D, 434.56587747522923D, 645.38727173326788D, 429.00814002756783D, 108.63500554177944D, 6.5702497703192817D, -0.060424415214858099D
		};
		double ad2[] = {
			-0.0098649429247000993D, -0.79928323768052301D, -17.757954917754752D, -160.63638485582192D, -637.56644336838963D, -1025.0951316110772D, -483.5191916086514D
		};
		double ad3[] = {
			30.338060743482458D, 325.79251299657392D, 1536.729586084437D, 3199.8582195085955D, 2553.0504064331644D, 474.52854120695537D, -22.440952446585818D
		};
		double d5 = d < 0.0D ? -d : d;
		double d2;
		if (d5 < 1.25D)
			d2 = 1.0D - error(d5);
		else
		if (d5 > 28D) {
			d2 = 0.0D;
		} else {
			double d1 = 1.0D / (d5 * d5);
			double d3;
			double d4;
			if (d5 < 2.8571428000000001D) {
				d3 = ad[0] + d1 * (ad[1] + d1 * (ad[2] + d1 * (ad[3] + d1 * (ad[4] + d1 * (ad[5] + d1 * (ad[6] + d1 * ad[7]))))));
				d4 = 1.0D + d1 * (ad1[0] + d1 * (ad1[1] + d1 * (ad1[2] + d1 * (ad1[3] + d1 * (ad1[4] + d1 * (ad1[5] + d1 * (ad1[6] + d1 * ad1[7])))))));
			} else {
				d3 = ad2[0] + d1 * (ad2[1] + d1 * (ad2[2] + d1 * (ad2[3] + d1 * (ad2[4] + d1 * (ad2[5] + d1 * ad2[6])))));
				d4 = 1.0D + d1 * (ad3[0] + d1 * (ad3[1] + d1 * (ad3[2] + d1 * (ad3[3] + d1 * (ad3[4] + d1 * (ad3[5] + d1 * ad3[6]))))));
			}
			d2 = Math.exp((-d * d - 0.5625D) + d3 / d4) / d5;
		}
		if (d >= 0.0D)
			return d2;
		else
			return 2D - d2;
	}

}
