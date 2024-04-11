module status_reg(clk, wreg,zl,cl,nl,z,c,n);
input clk, wreg, nl, zl, cl;
output n; reg n;
output z; reg z;
output c; reg c;
always @ (posedge clk)
if (wreg==1) begin
	n=nl; z=zl; c=cl ; 
end
endmodule
