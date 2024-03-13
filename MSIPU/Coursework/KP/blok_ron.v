module blok_ron (c,wreg,
d_bus, ax,ay,x,y);
input c,wreg ;
input [7:0] d_bus;
input [3:0] ax, ay;
output [7:0] x,y;
 reg [7:0] x,y;
reg [7:0] ron [3:0]; //1
 always begin //2
 x=ron[ax]; y=ron[ay]; end
 always @(posedge c) //3
if (wreg) ron[ax] = d_bus;
endmodule