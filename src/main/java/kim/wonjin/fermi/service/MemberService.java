package kim.wonjin.fermi.service;

public interface MemberService<T, ID> {

    T createMember(T member);

//    T getMember(ID id);

    T updateMember(T member);

    T deleteMember(T member);
}
