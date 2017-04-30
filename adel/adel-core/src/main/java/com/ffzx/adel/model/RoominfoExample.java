package com.ffzx.adel.model;

import com.ffzx.adel.model.RoominfoExample.Criteria;
import com.ffzx.orm.common.GenericExample.GeneratedCriteria;
import com.ffzx.orm.common.GenericExample;
import java.util.ArrayList;
import java.util.List;

public class RoominfoExample extends GenericExample<Criteria> {

    public RoominfoExample() {
        oredCriteria = new ArrayList<RoominfoExample.Criteria>();
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }

        public Criteria andRoomnoIsNull() {
            addCriterion("ROOMNO is null");
            return (Criteria) this;
        }

        public Criteria andRoomnoIsNotNull() {
            addCriterion("ROOMNO is not null");
            return (Criteria) this;
        }

        public Criteria andRoomnoEqualTo(String value) {
            addCriterion("ROOMNO =", value, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoNotEqualTo(String value) {
            addCriterion("ROOMNO <>", value, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoGreaterThan(String value) {
            addCriterion("ROOMNO >", value, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoGreaterThanOrEqualTo(String value) {
            addCriterion("ROOMNO >=", value, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoLessThan(String value) {
            addCriterion("ROOMNO <", value, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoLessThanOrEqualTo(String value) {
            addCriterion("ROOMNO <=", value, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoLike(String value) {
            addCriterion("ROOMNO like", value, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoNotLike(String value) {
            addCriterion("ROOMNO not like", value, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoIn(List<String> values) {
            addCriterion("ROOMNO in", values, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoNotIn(List<String> values) {
            addCriterion("ROOMNO not in", values, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoBetween(String value1, String value2) {
            addCriterion("ROOMNO between", value1, value2, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoNotBetween(String value1, String value2) {
            addCriterion("ROOMNO not between", value1, value2, "roomno");
            return (Criteria) this;
        }

        public Criteria andIndexnoIsNull() {
            addCriterion("INDEXNO is null");
            return (Criteria) this;
        }

        public Criteria andIndexnoIsNotNull() {
            addCriterion("INDEXNO is not null");
            return (Criteria) this;
        }

        public Criteria andIndexnoEqualTo(Integer value) {
            addCriterion("INDEXNO =", value, "indexno");
            return (Criteria) this;
        }

        public Criteria andIndexnoNotEqualTo(Integer value) {
            addCriterion("INDEXNO <>", value, "indexno");
            return (Criteria) this;
        }

        public Criteria andIndexnoGreaterThan(Integer value) {
            addCriterion("INDEXNO >", value, "indexno");
            return (Criteria) this;
        }

        public Criteria andIndexnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("INDEXNO >=", value, "indexno");
            return (Criteria) this;
        }

        public Criteria andIndexnoLessThan(Integer value) {
            addCriterion("INDEXNO <", value, "indexno");
            return (Criteria) this;
        }

        public Criteria andIndexnoLessThanOrEqualTo(Integer value) {
            addCriterion("INDEXNO <=", value, "indexno");
            return (Criteria) this;
        }

        public Criteria andIndexnoIn(List<Integer> values) {
            addCriterion("INDEXNO in", values, "indexno");
            return (Criteria) this;
        }

        public Criteria andIndexnoNotIn(List<Integer> values) {
            addCriterion("INDEXNO not in", values, "indexno");
            return (Criteria) this;
        }

        public Criteria andIndexnoBetween(Integer value1, Integer value2) {
            addCriterion("INDEXNO between", value1, value2, "indexno");
            return (Criteria) this;
        }

        public Criteria andIndexnoNotBetween(Integer value1, Integer value2) {
            addCriterion("INDEXNO not between", value1, value2, "indexno");
            return (Criteria) this;
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Short value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Short value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Short value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Short value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Short value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Short value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Short> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Short> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Short value1, Short value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Short value1, Short value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andLdqyIsNull() {
            addCriterion("LDQY is null");
            return (Criteria) this;
        }

        public Criteria andLdqyIsNotNull() {
            addCriterion("LDQY is not null");
            return (Criteria) this;
        }

        public Criteria andLdqyEqualTo(String value) {
            addCriterion("LDQY =", value, "ldqy");
            return (Criteria) this;
        }

        public Criteria andLdqyNotEqualTo(String value) {
            addCriterion("LDQY <>", value, "ldqy");
            return (Criteria) this;
        }

        public Criteria andLdqyGreaterThan(String value) {
            addCriterion("LDQY >", value, "ldqy");
            return (Criteria) this;
        }

        public Criteria andLdqyGreaterThanOrEqualTo(String value) {
            addCriterion("LDQY >=", value, "ldqy");
            return (Criteria) this;
        }

        public Criteria andLdqyLessThan(String value) {
            addCriterion("LDQY <", value, "ldqy");
            return (Criteria) this;
        }

        public Criteria andLdqyLessThanOrEqualTo(String value) {
            addCriterion("LDQY <=", value, "ldqy");
            return (Criteria) this;
        }

        public Criteria andLdqyLike(String value) {
            addCriterion("LDQY like", value, "ldqy");
            return (Criteria) this;
        }

        public Criteria andLdqyNotLike(String value) {
            addCriterion("LDQY not like", value, "ldqy");
            return (Criteria) this;
        }

        public Criteria andLdqyIn(List<String> values) {
            addCriterion("LDQY in", values, "ldqy");
            return (Criteria) this;
        }

        public Criteria andLdqyNotIn(List<String> values) {
            addCriterion("LDQY not in", values, "ldqy");
            return (Criteria) this;
        }

        public Criteria andLdqyBetween(String value1, String value2) {
            addCriterion("LDQY between", value1, value2, "ldqy");
            return (Criteria) this;
        }

        public Criteria andLdqyNotBetween(String value1, String value2) {
            addCriterion("LDQY not between", value1, value2, "ldqy");
            return (Criteria) this;
        }

        public Criteria andRoomnameIsNull() {
            addCriterion("ROOMNAME is null");
            return (Criteria) this;
        }

        public Criteria andRoomnameIsNotNull() {
            addCriterion("ROOMNAME is not null");
            return (Criteria) this;
        }

        public Criteria andRoomnameEqualTo(String value) {
            addCriterion("ROOMNAME =", value, "roomname");
            return (Criteria) this;
        }

        public Criteria andRoomnameNotEqualTo(String value) {
            addCriterion("ROOMNAME <>", value, "roomname");
            return (Criteria) this;
        }

        public Criteria andRoomnameGreaterThan(String value) {
            addCriterion("ROOMNAME >", value, "roomname");
            return (Criteria) this;
        }

        public Criteria andRoomnameGreaterThanOrEqualTo(String value) {
            addCriterion("ROOMNAME >=", value, "roomname");
            return (Criteria) this;
        }

        public Criteria andRoomnameLessThan(String value) {
            addCriterion("ROOMNAME <", value, "roomname");
            return (Criteria) this;
        }

        public Criteria andRoomnameLessThanOrEqualTo(String value) {
            addCriterion("ROOMNAME <=", value, "roomname");
            return (Criteria) this;
        }

        public Criteria andRoomnameLike(String value) {
            addCriterion("ROOMNAME like", value, "roomname");
            return (Criteria) this;
        }

        public Criteria andRoomnameNotLike(String value) {
            addCriterion("ROOMNAME not like", value, "roomname");
            return (Criteria) this;
        }

        public Criteria andRoomnameIn(List<String> values) {
            addCriterion("ROOMNAME in", values, "roomname");
            return (Criteria) this;
        }

        public Criteria andRoomnameNotIn(List<String> values) {
            addCriterion("ROOMNAME not in", values, "roomname");
            return (Criteria) this;
        }

        public Criteria andRoomnameBetween(String value1, String value2) {
            addCriterion("ROOMNAME between", value1, value2, "roomname");
            return (Criteria) this;
        }

        public Criteria andRoomnameNotBetween(String value1, String value2) {
            addCriterion("ROOMNAME not between", value1, value2, "roomname");
            return (Criteria) this;
        }

        public Criteria andRoomtypeIsNull() {
            addCriterion("ROOMTYPE is null");
            return (Criteria) this;
        }

        public Criteria andRoomtypeIsNotNull() {
            addCriterion("ROOMTYPE is not null");
            return (Criteria) this;
        }

        public Criteria andRoomtypeEqualTo(String value) {
            addCriterion("ROOMTYPE =", value, "roomtype");
            return (Criteria) this;
        }

        public Criteria andRoomtypeNotEqualTo(String value) {
            addCriterion("ROOMTYPE <>", value, "roomtype");
            return (Criteria) this;
        }

        public Criteria andRoomtypeGreaterThan(String value) {
            addCriterion("ROOMTYPE >", value, "roomtype");
            return (Criteria) this;
        }

        public Criteria andRoomtypeGreaterThanOrEqualTo(String value) {
            addCriterion("ROOMTYPE >=", value, "roomtype");
            return (Criteria) this;
        }

        public Criteria andRoomtypeLessThan(String value) {
            addCriterion("ROOMTYPE <", value, "roomtype");
            return (Criteria) this;
        }

        public Criteria andRoomtypeLessThanOrEqualTo(String value) {
            addCriterion("ROOMTYPE <=", value, "roomtype");
            return (Criteria) this;
        }

        public Criteria andRoomtypeLike(String value) {
            addCriterion("ROOMTYPE like", value, "roomtype");
            return (Criteria) this;
        }

        public Criteria andRoomtypeNotLike(String value) {
            addCriterion("ROOMTYPE not like", value, "roomtype");
            return (Criteria) this;
        }

        public Criteria andRoomtypeIn(List<String> values) {
            addCriterion("ROOMTYPE in", values, "roomtype");
            return (Criteria) this;
        }

        public Criteria andRoomtypeNotIn(List<String> values) {
            addCriterion("ROOMTYPE not in", values, "roomtype");
            return (Criteria) this;
        }

        public Criteria andRoomtypeBetween(String value1, String value2) {
            addCriterion("ROOMTYPE between", value1, value2, "roomtype");
            return (Criteria) this;
        }

        public Criteria andRoomtypeNotBetween(String value1, String value2) {
            addCriterion("ROOMTYPE not between", value1, value2, "roomtype");
            return (Criteria) this;
        }

        public Criteria andBednumIsNull() {
            addCriterion("BEDNUM is null");
            return (Criteria) this;
        }

        public Criteria andBednumIsNotNull() {
            addCriterion("BEDNUM is not null");
            return (Criteria) this;
        }

        public Criteria andBednumEqualTo(Short value) {
            addCriterion("BEDNUM =", value, "bednum");
            return (Criteria) this;
        }

        public Criteria andBednumNotEqualTo(Short value) {
            addCriterion("BEDNUM <>", value, "bednum");
            return (Criteria) this;
        }

        public Criteria andBednumGreaterThan(Short value) {
            addCriterion("BEDNUM >", value, "bednum");
            return (Criteria) this;
        }

        public Criteria andBednumGreaterThanOrEqualTo(Short value) {
            addCriterion("BEDNUM >=", value, "bednum");
            return (Criteria) this;
        }

        public Criteria andBednumLessThan(Short value) {
            addCriterion("BEDNUM <", value, "bednum");
            return (Criteria) this;
        }

        public Criteria andBednumLessThanOrEqualTo(Short value) {
            addCriterion("BEDNUM <=", value, "bednum");
            return (Criteria) this;
        }

        public Criteria andBednumIn(List<Short> values) {
            addCriterion("BEDNUM in", values, "bednum");
            return (Criteria) this;
        }

        public Criteria andBednumNotIn(List<Short> values) {
            addCriterion("BEDNUM not in", values, "bednum");
            return (Criteria) this;
        }

        public Criteria andBednumBetween(Short value1, Short value2) {
            addCriterion("BEDNUM between", value1, value2, "bednum");
            return (Criteria) this;
        }

        public Criteria andBednumNotBetween(Short value1, Short value2) {
            addCriterion("BEDNUM not between", value1, value2, "bednum");
            return (Criteria) this;
        }

        public Criteria andInnumIsNull() {
            addCriterion("INNUM is null");
            return (Criteria) this;
        }

        public Criteria andInnumIsNotNull() {
            addCriterion("INNUM is not null");
            return (Criteria) this;
        }

        public Criteria andInnumEqualTo(Short value) {
            addCriterion("INNUM =", value, "innum");
            return (Criteria) this;
        }

        public Criteria andInnumNotEqualTo(Short value) {
            addCriterion("INNUM <>", value, "innum");
            return (Criteria) this;
        }

        public Criteria andInnumGreaterThan(Short value) {
            addCriterion("INNUM >", value, "innum");
            return (Criteria) this;
        }

        public Criteria andInnumGreaterThanOrEqualTo(Short value) {
            addCriterion("INNUM >=", value, "innum");
            return (Criteria) this;
        }

        public Criteria andInnumLessThan(Short value) {
            addCriterion("INNUM <", value, "innum");
            return (Criteria) this;
        }

        public Criteria andInnumLessThanOrEqualTo(Short value) {
            addCriterion("INNUM <=", value, "innum");
            return (Criteria) this;
        }

        public Criteria andInnumIn(List<Short> values) {
            addCriterion("INNUM in", values, "innum");
            return (Criteria) this;
        }

        public Criteria andInnumNotIn(List<Short> values) {
            addCriterion("INNUM not in", values, "innum");
            return (Criteria) this;
        }

        public Criteria andInnumBetween(Short value1, Short value2) {
            addCriterion("INNUM between", value1, value2, "innum");
            return (Criteria) this;
        }

        public Criteria andInnumNotBetween(Short value1, Short value2) {
            addCriterion("INNUM not between", value1, value2, "innum");
            return (Criteria) this;
        }

        public Criteria andRecnumIsNull() {
            addCriterion("RECNUM is null");
            return (Criteria) this;
        }

        public Criteria andRecnumIsNotNull() {
            addCriterion("RECNUM is not null");
            return (Criteria) this;
        }

        public Criteria andRecnumEqualTo(Short value) {
            addCriterion("RECNUM =", value, "recnum");
            return (Criteria) this;
        }

        public Criteria andRecnumNotEqualTo(Short value) {
            addCriterion("RECNUM <>", value, "recnum");
            return (Criteria) this;
        }

        public Criteria andRecnumGreaterThan(Short value) {
            addCriterion("RECNUM >", value, "recnum");
            return (Criteria) this;
        }

        public Criteria andRecnumGreaterThanOrEqualTo(Short value) {
            addCriterion("RECNUM >=", value, "recnum");
            return (Criteria) this;
        }

        public Criteria andRecnumLessThan(Short value) {
            addCriterion("RECNUM <", value, "recnum");
            return (Criteria) this;
        }

        public Criteria andRecnumLessThanOrEqualTo(Short value) {
            addCriterion("RECNUM <=", value, "recnum");
            return (Criteria) this;
        }

        public Criteria andRecnumIn(List<Short> values) {
            addCriterion("RECNUM in", values, "recnum");
            return (Criteria) this;
        }

        public Criteria andRecnumNotIn(List<Short> values) {
            addCriterion("RECNUM not in", values, "recnum");
            return (Criteria) this;
        }

        public Criteria andRecnumBetween(Short value1, Short value2) {
            addCriterion("RECNUM between", value1, value2, "recnum");
            return (Criteria) this;
        }

        public Criteria andRecnumNotBetween(Short value1, Short value2) {
            addCriterion("RECNUM not between", value1, value2, "recnum");
            return (Criteria) this;
        }

        public Criteria andLcqyIsNull() {
            addCriterion("LCQY is null");
            return (Criteria) this;
        }

        public Criteria andLcqyIsNotNull() {
            addCriterion("LCQY is not null");
            return (Criteria) this;
        }

        public Criteria andLcqyEqualTo(String value) {
            addCriterion("LCQY =", value, "lcqy");
            return (Criteria) this;
        }

        public Criteria andLcqyNotEqualTo(String value) {
            addCriterion("LCQY <>", value, "lcqy");
            return (Criteria) this;
        }

        public Criteria andLcqyGreaterThan(String value) {
            addCriterion("LCQY >", value, "lcqy");
            return (Criteria) this;
        }

        public Criteria andLcqyGreaterThanOrEqualTo(String value) {
            addCriterion("LCQY >=", value, "lcqy");
            return (Criteria) this;
        }

        public Criteria andLcqyLessThan(String value) {
            addCriterion("LCQY <", value, "lcqy");
            return (Criteria) this;
        }

        public Criteria andLcqyLessThanOrEqualTo(String value) {
            addCriterion("LCQY <=", value, "lcqy");
            return (Criteria) this;
        }

        public Criteria andLcqyLike(String value) {
            addCriterion("LCQY like", value, "lcqy");
            return (Criteria) this;
        }

        public Criteria andLcqyNotLike(String value) {
            addCriterion("LCQY not like", value, "lcqy");
            return (Criteria) this;
        }

        public Criteria andLcqyIn(List<String> values) {
            addCriterion("LCQY in", values, "lcqy");
            return (Criteria) this;
        }

        public Criteria andLcqyNotIn(List<String> values) {
            addCriterion("LCQY not in", values, "lcqy");
            return (Criteria) this;
        }

        public Criteria andLcqyBetween(String value1, String value2) {
            addCriterion("LCQY between", value1, value2, "lcqy");
            return (Criteria) this;
        }

        public Criteria andLcqyNotBetween(String value1, String value2) {
            addCriterion("LCQY not between", value1, value2, "lcqy");
            return (Criteria) this;
        }

        public Criteria andLbqyIsNull() {
            addCriterion("LBQY is null");
            return (Criteria) this;
        }

        public Criteria andLbqyIsNotNull() {
            addCriterion("LBQY is not null");
            return (Criteria) this;
        }

        public Criteria andLbqyEqualTo(String value) {
            addCriterion("LBQY =", value, "lbqy");
            return (Criteria) this;
        }

        public Criteria andLbqyNotEqualTo(String value) {
            addCriterion("LBQY <>", value, "lbqy");
            return (Criteria) this;
        }

        public Criteria andLbqyGreaterThan(String value) {
            addCriterion("LBQY >", value, "lbqy");
            return (Criteria) this;
        }

        public Criteria andLbqyGreaterThanOrEqualTo(String value) {
            addCriterion("LBQY >=", value, "lbqy");
            return (Criteria) this;
        }

        public Criteria andLbqyLessThan(String value) {
            addCriterion("LBQY <", value, "lbqy");
            return (Criteria) this;
        }

        public Criteria andLbqyLessThanOrEqualTo(String value) {
            addCriterion("LBQY <=", value, "lbqy");
            return (Criteria) this;
        }

        public Criteria andLbqyLike(String value) {
            addCriterion("LBQY like", value, "lbqy");
            return (Criteria) this;
        }

        public Criteria andLbqyNotLike(String value) {
            addCriterion("LBQY not like", value, "lbqy");
            return (Criteria) this;
        }

        public Criteria andLbqyIn(List<String> values) {
            addCriterion("LBQY in", values, "lbqy");
            return (Criteria) this;
        }

        public Criteria andLbqyNotIn(List<String> values) {
            addCriterion("LBQY not in", values, "lbqy");
            return (Criteria) this;
        }

        public Criteria andLbqyBetween(String value1, String value2) {
            addCriterion("LBQY between", value1, value2, "lbqy");
            return (Criteria) this;
        }

        public Criteria andLbqyNotBetween(String value1, String value2) {
            addCriterion("LBQY not between", value1, value2, "lbqy");
            return (Criteria) this;
        }

        public Criteria andQjqyIsNull() {
            addCriterion("QJQY is null");
            return (Criteria) this;
        }

        public Criteria andQjqyIsNotNull() {
            addCriterion("QJQY is not null");
            return (Criteria) this;
        }

        public Criteria andQjqyEqualTo(String value) {
            addCriterion("QJQY =", value, "qjqy");
            return (Criteria) this;
        }

        public Criteria andQjqyNotEqualTo(String value) {
            addCriterion("QJQY <>", value, "qjqy");
            return (Criteria) this;
        }

        public Criteria andQjqyGreaterThan(String value) {
            addCriterion("QJQY >", value, "qjqy");
            return (Criteria) this;
        }

        public Criteria andQjqyGreaterThanOrEqualTo(String value) {
            addCriterion("QJQY >=", value, "qjqy");
            return (Criteria) this;
        }

        public Criteria andQjqyLessThan(String value) {
            addCriterion("QJQY <", value, "qjqy");
            return (Criteria) this;
        }

        public Criteria andQjqyLessThanOrEqualTo(String value) {
            addCriterion("QJQY <=", value, "qjqy");
            return (Criteria) this;
        }

        public Criteria andQjqyLike(String value) {
            addCriterion("QJQY like", value, "qjqy");
            return (Criteria) this;
        }

        public Criteria andQjqyNotLike(String value) {
            addCriterion("QJQY not like", value, "qjqy");
            return (Criteria) this;
        }

        public Criteria andQjqyIn(List<String> values) {
            addCriterion("QJQY in", values, "qjqy");
            return (Criteria) this;
        }

        public Criteria andQjqyNotIn(List<String> values) {
            addCriterion("QJQY not in", values, "qjqy");
            return (Criteria) this;
        }

        public Criteria andQjqyBetween(String value1, String value2) {
            addCriterion("QJQY between", value1, value2, "qjqy");
            return (Criteria) this;
        }

        public Criteria andQjqyNotBetween(String value1, String value2) {
            addCriterion("QJQY not between", value1, value2, "qjqy");
            return (Criteria) this;
        }

        public Criteria andParentIsNull() {
            addCriterion("PARENT is null");
            return (Criteria) this;
        }

        public Criteria andParentIsNotNull() {
            addCriterion("PARENT is not null");
            return (Criteria) this;
        }

        public Criteria andParentEqualTo(String value) {
            addCriterion("PARENT =", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentNotEqualTo(String value) {
            addCriterion("PARENT <>", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentGreaterThan(String value) {
            addCriterion("PARENT >", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentGreaterThanOrEqualTo(String value) {
            addCriterion("PARENT >=", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentLessThan(String value) {
            addCriterion("PARENT <", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentLessThanOrEqualTo(String value) {
            addCriterion("PARENT <=", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentLike(String value) {
            addCriterion("PARENT like", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentNotLike(String value) {
            addCriterion("PARENT not like", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentIn(List<String> values) {
            addCriterion("PARENT in", values, "parent");
            return (Criteria) this;
        }

        public Criteria andParentNotIn(List<String> values) {
            addCriterion("PARENT not in", values, "parent");
            return (Criteria) this;
        }

        public Criteria andParentBetween(String value1, String value2) {
            addCriterion("PARENT between", value1, value2, "parent");
            return (Criteria) this;
        }

        public Criteria andParentNotBetween(String value1, String value2) {
            addCriterion("PARENT not between", value1, value2, "parent");
            return (Criteria) this;
        }

        public Criteria andLockflagIsNull() {
            addCriterion("LOCKFLAG is null");
            return (Criteria) this;
        }

        public Criteria andLockflagIsNotNull() {
            addCriterion("LOCKFLAG is not null");
            return (Criteria) this;
        }

        public Criteria andLockflagEqualTo(Boolean value) {
            addCriterion("LOCKFLAG =", value, "lockflag");
            return (Criteria) this;
        }

        public Criteria andLockflagNotEqualTo(Boolean value) {
            addCriterion("LOCKFLAG <>", value, "lockflag");
            return (Criteria) this;
        }

        public Criteria andLockflagGreaterThan(Boolean value) {
            addCriterion("LOCKFLAG >", value, "lockflag");
            return (Criteria) this;
        }

        public Criteria andLockflagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("LOCKFLAG >=", value, "lockflag");
            return (Criteria) this;
        }

        public Criteria andLockflagLessThan(Boolean value) {
            addCriterion("LOCKFLAG <", value, "lockflag");
            return (Criteria) this;
        }

        public Criteria andLockflagLessThanOrEqualTo(Boolean value) {
            addCriterion("LOCKFLAG <=", value, "lockflag");
            return (Criteria) this;
        }

        public Criteria andLockflagIn(List<Boolean> values) {
            addCriterion("LOCKFLAG in", values, "lockflag");
            return (Criteria) this;
        }

        public Criteria andLockflagNotIn(List<Boolean> values) {
            addCriterion("LOCKFLAG not in", values, "lockflag");
            return (Criteria) this;
        }

        public Criteria andLockflagBetween(Boolean value1, Boolean value2) {
            addCriterion("LOCKFLAG between", value1, value2, "lockflag");
            return (Criteria) this;
        }

        public Criteria andLockflagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("LOCKFLAG not between", value1, value2, "lockflag");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Short> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Short> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andRecstatusIsNull() {
            addCriterion("RECSTATUS is null");
            return (Criteria) this;
        }

        public Criteria andRecstatusIsNotNull() {
            addCriterion("RECSTATUS is not null");
            return (Criteria) this;
        }

        public Criteria andRecstatusEqualTo(Short value) {
            addCriterion("RECSTATUS =", value, "recstatus");
            return (Criteria) this;
        }

        public Criteria andRecstatusNotEqualTo(Short value) {
            addCriterion("RECSTATUS <>", value, "recstatus");
            return (Criteria) this;
        }

        public Criteria andRecstatusGreaterThan(Short value) {
            addCriterion("RECSTATUS >", value, "recstatus");
            return (Criteria) this;
        }

        public Criteria andRecstatusGreaterThanOrEqualTo(Short value) {
            addCriterion("RECSTATUS >=", value, "recstatus");
            return (Criteria) this;
        }

        public Criteria andRecstatusLessThan(Short value) {
            addCriterion("RECSTATUS <", value, "recstatus");
            return (Criteria) this;
        }

        public Criteria andRecstatusLessThanOrEqualTo(Short value) {
            addCriterion("RECSTATUS <=", value, "recstatus");
            return (Criteria) this;
        }

        public Criteria andRecstatusIn(List<Short> values) {
            addCriterion("RECSTATUS in", values, "recstatus");
            return (Criteria) this;
        }

        public Criteria andRecstatusNotIn(List<Short> values) {
            addCriterion("RECSTATUS not in", values, "recstatus");
            return (Criteria) this;
        }

        public Criteria andRecstatusBetween(Short value1, Short value2) {
            addCriterion("RECSTATUS between", value1, value2, "recstatus");
            return (Criteria) this;
        }

        public Criteria andRecstatusNotBetween(Short value1, Short value2) {
            addCriterion("RECSTATUS not between", value1, value2, "recstatus");
            return (Criteria) this;
        }

        public Criteria andLocktypeIsNull() {
            addCriterion("LOCKTYPE is null");
            return (Criteria) this;
        }

        public Criteria andLocktypeIsNotNull() {
            addCriterion("LOCKTYPE is not null");
            return (Criteria) this;
        }

        public Criteria andLocktypeEqualTo(Short value) {
            addCriterion("LOCKTYPE =", value, "locktype");
            return (Criteria) this;
        }

        public Criteria andLocktypeNotEqualTo(Short value) {
            addCriterion("LOCKTYPE <>", value, "locktype");
            return (Criteria) this;
        }

        public Criteria andLocktypeGreaterThan(Short value) {
            addCriterion("LOCKTYPE >", value, "locktype");
            return (Criteria) this;
        }

        public Criteria andLocktypeGreaterThanOrEqualTo(Short value) {
            addCriterion("LOCKTYPE >=", value, "locktype");
            return (Criteria) this;
        }

        public Criteria andLocktypeLessThan(Short value) {
            addCriterion("LOCKTYPE <", value, "locktype");
            return (Criteria) this;
        }

        public Criteria andLocktypeLessThanOrEqualTo(Short value) {
            addCriterion("LOCKTYPE <=", value, "locktype");
            return (Criteria) this;
        }

        public Criteria andLocktypeIn(List<Short> values) {
            addCriterion("LOCKTYPE in", values, "locktype");
            return (Criteria) this;
        }

        public Criteria andLocktypeNotIn(List<Short> values) {
            addCriterion("LOCKTYPE not in", values, "locktype");
            return (Criteria) this;
        }

        public Criteria andLocktypeBetween(Short value1, Short value2) {
            addCriterion("LOCKTYPE between", value1, value2, "locktype");
            return (Criteria) this;
        }

        public Criteria andLocktypeNotBetween(Short value1, Short value2) {
            addCriterion("LOCKTYPE not between", value1, value2, "locktype");
            return (Criteria) this;
        }

        public Criteria andPmsnoIsNull() {
            addCriterion("PMSNO is null");
            return (Criteria) this;
        }

        public Criteria andPmsnoIsNotNull() {
            addCriterion("PMSNO is not null");
            return (Criteria) this;
        }

        public Criteria andPmsnoEqualTo(String value) {
            addCriterion("PMSNO =", value, "pmsno");
            return (Criteria) this;
        }

        public Criteria andPmsnoNotEqualTo(String value) {
            addCriterion("PMSNO <>", value, "pmsno");
            return (Criteria) this;
        }

        public Criteria andPmsnoGreaterThan(String value) {
            addCriterion("PMSNO >", value, "pmsno");
            return (Criteria) this;
        }

        public Criteria andPmsnoGreaterThanOrEqualTo(String value) {
            addCriterion("PMSNO >=", value, "pmsno");
            return (Criteria) this;
        }

        public Criteria andPmsnoLessThan(String value) {
            addCriterion("PMSNO <", value, "pmsno");
            return (Criteria) this;
        }

        public Criteria andPmsnoLessThanOrEqualTo(String value) {
            addCriterion("PMSNO <=", value, "pmsno");
            return (Criteria) this;
        }

        public Criteria andPmsnoLike(String value) {
            addCriterion("PMSNO like", value, "pmsno");
            return (Criteria) this;
        }

        public Criteria andPmsnoNotLike(String value) {
            addCriterion("PMSNO not like", value, "pmsno");
            return (Criteria) this;
        }

        public Criteria andPmsnoIn(List<String> values) {
            addCriterion("PMSNO in", values, "pmsno");
            return (Criteria) this;
        }

        public Criteria andPmsnoNotIn(List<String> values) {
            addCriterion("PMSNO not in", values, "pmsno");
            return (Criteria) this;
        }

        public Criteria andPmsnoBetween(String value1, String value2) {
            addCriterion("PMSNO between", value1, value2, "pmsno");
            return (Criteria) this;
        }

        public Criteria andPmsnoNotBetween(String value1, String value2) {
            addCriterion("PMSNO not between", value1, value2, "pmsno");
            return (Criteria) this;
        }

        public Criteria andGatewayidIsNull() {
            addCriterion("GATEWAYID is null");
            return (Criteria) this;
        }

        public Criteria andGatewayidIsNotNull() {
            addCriterion("GATEWAYID is not null");
            return (Criteria) this;
        }

        public Criteria andGatewayidEqualTo(Integer value) {
            addCriterion("GATEWAYID =", value, "gatewayid");
            return (Criteria) this;
        }

        public Criteria andGatewayidNotEqualTo(Integer value) {
            addCriterion("GATEWAYID <>", value, "gatewayid");
            return (Criteria) this;
        }

        public Criteria andGatewayidGreaterThan(Integer value) {
            addCriterion("GATEWAYID >", value, "gatewayid");
            return (Criteria) this;
        }

        public Criteria andGatewayidGreaterThanOrEqualTo(Integer value) {
            addCriterion("GATEWAYID >=", value, "gatewayid");
            return (Criteria) this;
        }

        public Criteria andGatewayidLessThan(Integer value) {
            addCriterion("GATEWAYID <", value, "gatewayid");
            return (Criteria) this;
        }

        public Criteria andGatewayidLessThanOrEqualTo(Integer value) {
            addCriterion("GATEWAYID <=", value, "gatewayid");
            return (Criteria) this;
        }

        public Criteria andGatewayidIn(List<Integer> values) {
            addCriterion("GATEWAYID in", values, "gatewayid");
            return (Criteria) this;
        }

        public Criteria andGatewayidNotIn(List<Integer> values) {
            addCriterion("GATEWAYID not in", values, "gatewayid");
            return (Criteria) this;
        }

        public Criteria andGatewayidBetween(Integer value1, Integer value2) {
            addCriterion("GATEWAYID between", value1, value2, "gatewayid");
            return (Criteria) this;
        }

        public Criteria andGatewayidNotBetween(Integer value1, Integer value2) {
            addCriterion("GATEWAYID not between", value1, value2, "gatewayid");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("ADDRESS is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("ADDRESS is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("ADDRESS =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("ADDRESS <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("ADDRESS >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("ADDRESS >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("ADDRESS <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("ADDRESS <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("ADDRESS like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("ADDRESS not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("ADDRESS in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("ADDRESS not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("ADDRESS between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("ADDRESS not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andChannel1IsNull() {
            addCriterion("CHANNEL1 is null");
            return (Criteria) this;
        }

        public Criteria andChannel1IsNotNull() {
            addCriterion("CHANNEL1 is not null");
            return (Criteria) this;
        }

        public Criteria andChannel1EqualTo(Integer value) {
            addCriterion("CHANNEL1 =", value, "channel1");
            return (Criteria) this;
        }

        public Criteria andChannel1NotEqualTo(Integer value) {
            addCriterion("CHANNEL1 <>", value, "channel1");
            return (Criteria) this;
        }

        public Criteria andChannel1GreaterThan(Integer value) {
            addCriterion("CHANNEL1 >", value, "channel1");
            return (Criteria) this;
        }

        public Criteria andChannel1GreaterThanOrEqualTo(Integer value) {
            addCriterion("CHANNEL1 >=", value, "channel1");
            return (Criteria) this;
        }

        public Criteria andChannel1LessThan(Integer value) {
            addCriterion("CHANNEL1 <", value, "channel1");
            return (Criteria) this;
        }

        public Criteria andChannel1LessThanOrEqualTo(Integer value) {
            addCriterion("CHANNEL1 <=", value, "channel1");
            return (Criteria) this;
        }

        public Criteria andChannel1In(List<Integer> values) {
            addCriterion("CHANNEL1 in", values, "channel1");
            return (Criteria) this;
        }

        public Criteria andChannel1NotIn(List<Integer> values) {
            addCriterion("CHANNEL1 not in", values, "channel1");
            return (Criteria) this;
        }

        public Criteria andChannel1Between(Integer value1, Integer value2) {
            addCriterion("CHANNEL1 between", value1, value2, "channel1");
            return (Criteria) this;
        }

        public Criteria andChannel1NotBetween(Integer value1, Integer value2) {
            addCriterion("CHANNEL1 not between", value1, value2, "channel1");
            return (Criteria) this;
        }

        public Criteria andChannel2IsNull() {
            addCriterion("CHANNEL2 is null");
            return (Criteria) this;
        }

        public Criteria andChannel2IsNotNull() {
            addCriterion("CHANNEL2 is not null");
            return (Criteria) this;
        }

        public Criteria andChannel2EqualTo(Integer value) {
            addCriterion("CHANNEL2 =", value, "channel2");
            return (Criteria) this;
        }

        public Criteria andChannel2NotEqualTo(Integer value) {
            addCriterion("CHANNEL2 <>", value, "channel2");
            return (Criteria) this;
        }

        public Criteria andChannel2GreaterThan(Integer value) {
            addCriterion("CHANNEL2 >", value, "channel2");
            return (Criteria) this;
        }

        public Criteria andChannel2GreaterThanOrEqualTo(Integer value) {
            addCriterion("CHANNEL2 >=", value, "channel2");
            return (Criteria) this;
        }

        public Criteria andChannel2LessThan(Integer value) {
            addCriterion("CHANNEL2 <", value, "channel2");
            return (Criteria) this;
        }

        public Criteria andChannel2LessThanOrEqualTo(Integer value) {
            addCriterion("CHANNEL2 <=", value, "channel2");
            return (Criteria) this;
        }

        public Criteria andChannel2In(List<Integer> values) {
            addCriterion("CHANNEL2 in", values, "channel2");
            return (Criteria) this;
        }

        public Criteria andChannel2NotIn(List<Integer> values) {
            addCriterion("CHANNEL2 not in", values, "channel2");
            return (Criteria) this;
        }

        public Criteria andChannel2Between(Integer value1, Integer value2) {
            addCriterion("CHANNEL2 between", value1, value2, "channel2");
            return (Criteria) this;
        }

        public Criteria andChannel2NotBetween(Integer value1, Integer value2) {
            addCriterion("CHANNEL2 not between", value1, value2, "channel2");
            return (Criteria) this;
        }

        public Criteria andNetlockflagIsNull() {
            addCriterion("NETLOCKFLAG is null");
            return (Criteria) this;
        }

        public Criteria andNetlockflagIsNotNull() {
            addCriterion("NETLOCKFLAG is not null");
            return (Criteria) this;
        }

        public Criteria andNetlockflagEqualTo(Integer value) {
            addCriterion("NETLOCKFLAG =", value, "netlockflag");
            return (Criteria) this;
        }

        public Criteria andNetlockflagNotEqualTo(Integer value) {
            addCriterion("NETLOCKFLAG <>", value, "netlockflag");
            return (Criteria) this;
        }

        public Criteria andNetlockflagGreaterThan(Integer value) {
            addCriterion("NETLOCKFLAG >", value, "netlockflag");
            return (Criteria) this;
        }

        public Criteria andNetlockflagGreaterThanOrEqualTo(Integer value) {
            addCriterion("NETLOCKFLAG >=", value, "netlockflag");
            return (Criteria) this;
        }

        public Criteria andNetlockflagLessThan(Integer value) {
            addCriterion("NETLOCKFLAG <", value, "netlockflag");
            return (Criteria) this;
        }

        public Criteria andNetlockflagLessThanOrEqualTo(Integer value) {
            addCriterion("NETLOCKFLAG <=", value, "netlockflag");
            return (Criteria) this;
        }

        public Criteria andNetlockflagIn(List<Integer> values) {
            addCriterion("NETLOCKFLAG in", values, "netlockflag");
            return (Criteria) this;
        }

        public Criteria andNetlockflagNotIn(List<Integer> values) {
            addCriterion("NETLOCKFLAG not in", values, "netlockflag");
            return (Criteria) this;
        }

        public Criteria andNetlockflagBetween(Integer value1, Integer value2) {
            addCriterion("NETLOCKFLAG between", value1, value2, "netlockflag");
            return (Criteria) this;
        }

        public Criteria andNetlockflagNotBetween(Integer value1, Integer value2) {
            addCriterion("NETLOCKFLAG not between", value1, value2, "netlockflag");
            return (Criteria) this;
        }
    }
}