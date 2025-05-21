package com.jdc.online.balances.model;

import java.util.ArrayList;
import java.util.List;

public record PageResult<T>(
		List<T> contents,
		long count,
		int size,
		int page
		) {

	public int getTotalPageCount() {
		var page = (count % size) != 0 ? count / size + 1 : count / size;
		return (int) page;
	}
	
	public List<Integer> getPageLinks() {
		var totalPages = getTotalPageCount();
		var links = new ArrayList<Integer>();
		links.add(page);
		
		if (totalPages > 0) {
			while (links.getFirst() > 0 && links.size() < 3) {
				links.addFirst(links.getFirst() - 1);
			}
			while (links.getLast() < totalPages - 1 && links.size() < 5) {
				links.addLast(links.getLast() + 1);
			}
			while (links.getFirst() > 0 && links.size() < 5) {
				links.addFirst(links.getFirst() - 1);
			} 
		}
		return links;
	}
	
	public static class Builder<T> {
        private List<T> contents;
        private long count;
        private int size;
        private int page;

        public Builder<T> contents(List<T> contents) {
            this.contents = contents;
            return this;
        }

        public Builder<T> count(long count) {
            this.count = count;
            return this;
        }

        public Builder<T> size(int size) {
            this.size = size;
            return this;
        }

        public Builder<T> page(int page) {
            this.page = page;
            return this;
        }

        public PageResult<T> build() {
            return new PageResult<>(contents, count, size, page);
        }
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

}
