package com.ibm.gbsc.firm.reportshow;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.ibm.gbsc.utils.util.DateUtilsExt;

public enum ReportFreqence {
	YEAR {
		@Override
		public Date roundReportDate(Date date, int dataPeriods) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			if (cal.get(Calendar.MONTH) != Calendar.DECEMBER || cal.get(Calendar.DATE) != 31) {
				cal.add(Calendar.YEAR, -1);
				cal.set(Calendar.MONTH, Calendar.DECEMBER);
				cal.set(Calendar.DATE, 31);
				// date = cal.getTime();
			}
			// change according dataPeriods
			if (dataPeriods > 0) {
				cal.add(Calendar.YEAR, -dataPeriods);
				// go to next year begin.
				cal.add(Calendar.DATE, 1);
			}
			return cal.getTime();
		}
	},
	SEMI_YEAR {
		@Override
		public Date roundReportDate(Date date, int dataPeriods) {
			// TODO Auto-generated method stub
			return date;
		}
	},
	QUARTER {
		@Override
		public Date roundReportDate(Date date, int dataPeriods) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			DateUtilsExt.goLastMonthEnd(cal);
			// is quarter month?
			int mnth = cal.get(Calendar.MONTH);
			int lessMonths = (mnth + 1) % 3;
			if (lessMonths > 0) {// not a quarter month
				cal.add(Calendar.MONTH, -lessMonths);
			}
			// change according dataPeriods
			if (dataPeriods > 0) {
				cal.add(Calendar.MONTH, -dataPeriods * 3);
				// DateUtilsExt.goNextMonthBegin(cal);
			} else {
				DateUtilsExt.goMonthEnd(cal);
			}
			return cal.getTime();
		}
	},
	BI_MONTH {
		@Override
		public Date roundReportDate(Date date, int dataPeriods) {
			// TODO Auto-generated method stub
			return date;
		}
	},
	MONTH {
		@Override
		public Date roundReportDate(Date date, int dataPeriods) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			// change to last monthend
			// DateUtilsExt.goLastMonthEnd(cal);
			if (dataPeriods == 0) {
				return cal.getTime();
			}
			// 减掉指定期数
			cal.add(Calendar.MONTH, -dataPeriods);
			// round to month-end
			// DateUtilsExt.goNextMonthBegin(cal);
			return cal.getTime();
		}

	},
	SEMI_MONTH {
		@Override
		public Date roundReportDate(Date date, int dataPeriods) {
			// TODO
			return date;
		}
	},
	BI_WEEK {
		@Override
		public Date roundReportDate(Date date, int dataPeriods) {
			// TODO Auto-generated method stub
			return date;
		}
	},
	WEEK {
		@Override
		public Date roundReportDate(Date date, int dataPeriods) {
			// TODO Auto-generated method stub
			return date;
		}
	},
	DAILY {
		@Override
		public Date roundReportDate(Date date, int dataPeriods) {
			return dataPeriods == 0 ? date : DateUtils.addDays(date, -dataPeriods);
		}
	};
	/**
	 * 将输入的报告日期规约到最近的上一报告期末日期，如输入报告日期为2012-05-31，季度报表的日期应该为2012-03-31
	 *
	 * @param date
	 *            选择的报告日期
	 * @param dataPeriods
	 *            数据期数，0表示末期，其它数字则减去期数求开始日期
	 * @return 最近的上一报告期末日期
	 */
	public abstract Date roundReportDate(Date date, int dataPeriods);
}
