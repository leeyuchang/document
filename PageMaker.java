package com.jpa1.jpa1.vo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(exclude = "pageList")
public class PageMaker<T> {

  private Page<T> result;

  private Pageable prevPage;
  private Pageable nextPage;

  private int currentPageNum;
  private int totalPageNum;

  private Pageable currentPage;
  private List<Pageable> pageList;

  public PageMaker(Page<T> result) {
    this.result = result;
    this.currentPage = result.getPageable();
    this.currentPageNum = currentPage.getPageNumber();
    this.totalPageNum = result.getTotalPages();
    this.pageList = new ArrayList<>();
    calcPages();
  }

  private void calcPages() {
    int endPageNum = (int) (Math.ceil((currentPageNum + 1) / 5.0) * 5);
    int startPageNum = endPageNum - 4;

    Pageable startPage = currentPage;

    for (int i = startPageNum; i < currentPageNum + 1; i++) {
      startPage = startPage.previousOrFirst();
    }
    prevPage = startPage.getPageNumber() == 0 ? null : startPage.previousOrFirst();

    if (totalPageNum < endPageNum) {
      endPageNum = totalPageNum;
    }

    for (int i = startPageNum; i <= endPageNum; i++) {
      pageList.add(startPage);
      startPage = startPage.next();
    }

    nextPage = startPage.getPageNumber() < totalPageNum ? startPage : null;
  }
}
