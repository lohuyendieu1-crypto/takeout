package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {

    /**
     * 統計營業額
     * @param begin
     * @param end
     * @return
     */
    TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end);


    /**
     * 根據時間區間統計用戶數量
     * @param begin
     * @param end
     * @return
     */
    UserReportVO getUserStatistics(LocalDate begin, LocalDate end);

    /**
     * 根據時間區間統計訂單數量
     * @param begin
     * @param end
     * @return
     */
    OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end);


    /**
     * 查詢指定時間區間內的銷量排名top10
     * @param begin
     * @param end
     * @return
     */
    SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end);
}
