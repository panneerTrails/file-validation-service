package com.file.validation.model;

public class RecordDesc {

    public long reference;
    public String accountNumber;
    public String description;
    public double startBalance;
    public double mutation;
    public double endBalance;

    public long getReference() {
        return reference;
    }

    public RecordDesc setReference(long reference) {
        this.reference = reference;
        return this;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public RecordDesc setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RecordDesc setDescription(String description) {
        this.description = description;
        return this;
    }

    public double getStartBalance() {
        return startBalance;
    }

    public RecordDesc setStartBalance(double startBalance) {
        this.startBalance = startBalance;
        return this;
    }

    public double getMutation() {
        return mutation;
    }

    public RecordDesc setMutation(double mutation) {
        this.mutation = mutation;
        return this;
    }

    public double getEndBalance() {
        return endBalance;
    }

    public RecordDesc setEndBalance(double endBalance) {
        this.endBalance = endBalance;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RecordDesc{");
        sb.append("reference=").append(reference);
        sb.append(", accountNumber='").append(accountNumber).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", startBalance=").append(startBalance);
        sb.append(", mutation=").append(mutation);
        sb.append(", endBalance=").append(endBalance);
        sb.append('}');
        return sb.toString();
    }
}
